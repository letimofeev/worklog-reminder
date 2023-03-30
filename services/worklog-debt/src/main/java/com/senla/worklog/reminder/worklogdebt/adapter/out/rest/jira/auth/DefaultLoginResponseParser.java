package com.senla.worklog.reminder.worklogdebt.adapter.out.rest.jira.auth;

import com.senla.worklog.reminder.worklogdebt.adapter.out.rest.jira.auth.session.JiraSession;
import org.jsoup.Jsoup;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultLoginResponseParser implements LoginResponseParser {
    private static final String UNEXPECTED_ERROR_MESSAGE = "Unexpected error occurred";

    @Override
    public JiraSession parseLoginResponse(ResponseEntity<String> response) {
        validateLoginResponse(response);
        return getJiraSession(response);
    }

    private JiraSession getJiraSession(ResponseEntity<String> response) {
        var cookies = getCookies(response);
        var sessionId = findSessionId(cookies);
        return new JiraSession(sessionId);
    }

    private List<String> getCookies(ResponseEntity<String> response) {
        var setCookies = response.getHeaders().get("Set-Cookie");
        if (setCookies == null) {
            throw new JiraAuthenticationException("Response on login request does not contain Set-Cookie header");
        }
        return setCookies;
    }

    private String findSessionId(List<String> cookies) {
        return cookies.stream()
                .filter(cookie -> cookie.startsWith("JSESSIONID"))
                .map(cookie -> cookie.split(";")[0])
                .findFirst()
                .orElseThrow(() -> new JiraAuthenticationException("JSESSIONID not found in Set-Cookie login response"));
    }

    private void validateLoginResponse(ResponseEntity<String> response) {
        if (!response.getStatusCode().is3xxRedirection()) {
            var errorMessage = resolveLoginErrorMessage(response.getBody());
            throw new JiraAuthenticationException("Login failed; error message: " + errorMessage);
        }
    }

    private String resolveLoginErrorMessage(String responseBody) {
        var document = Jsoup.parse(responseBody);
        var formBody = document.getElementsByClass("form-body").first();
        if (formBody == null) {
            return UNEXPECTED_ERROR_MESSAGE;
        }
        return formBody.getElementsByClass("aui-message error").text();
    }
}
