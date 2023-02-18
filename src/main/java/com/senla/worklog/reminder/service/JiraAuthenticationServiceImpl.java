package com.senla.worklog.reminder.service;

import com.senla.worklog.reminder.config.JiraProperties;
import com.senla.worklog.reminder.exception.JiraAuthenticationException;
import com.senla.worklog.reminder.model.JiraSession;
import com.senla.worklog.reminder.repository.JiraSessionStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static liquibase.repackaged.org.apache.commons.lang3.StringUtils.substringBefore;

@Slf4j
@Service
@RequiredArgsConstructor
public class JiraAuthenticationServiceImpl implements JiraAuthenticationService {
    private final RestTemplate restTemplate;
    private final JiraSessionStorage sessionStorage;
    private final JiraProperties jiraProperties;

    private final LoginResponseParser responseParser = new LoginResponseParser();

    @Override
    public JiraSession login() {
        try {
            String loginUrl = jiraProperties.getLoginUrl();
            HttpEntity<String> request = getLoginRequest();
            ResponseEntity<String> response = postLoginRequest(loginUrl, request);
            JiraSession session = responseParser.parseLoginResponse(response);
            sessionStorage.saveSession(session);
            return session;
        } catch (Exception e) {
            throw new JiraAuthenticationException("Exception during Jira login; nested exception: " + e, e);
        }
    }

    @Override
    public JiraSession getSession() {
        return getSessionInternal();
    }

    @Override
    public HttpHeaders getAuthenticationHeaders() {
        HttpHeaders headers = new HttpHeaders();
        addBasicAuthHeader(headers);
        addSessionIdHeader(headers);
        return headers;
    }

    private HttpEntity<String> getLoginRequest() {
        HttpHeaders headers = getLoginRequestHeaders();
        String requestBody = getLoginRequestBody();
        return new HttpEntity<>(requestBody, headers);
    }

    private HttpHeaders getLoginRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        addBasicAuthHeader(headers);
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        return headers;
    }

    private void addBasicAuthHeader(HttpHeaders headers) {
        String basicAuthUsername = jiraProperties.getBasicAuth().getUsername();
        String basicAuthPassword = jiraProperties.getBasicAuth().getPassword();
        headers.setBasicAuth(basicAuthUsername, basicAuthPassword);
    }

    private void addSessionIdHeader(HttpHeaders headers) {
        headers.set("Cookie", getSessionInternal().getSessionId());
    }

    private JiraSession getSessionInternal() {
        return sessionStorage.getSession()
                .orElseGet(() -> {
                    log.info("Session not found in storage, performing a login");
                    return login();
                });
    }

    private String getLoginRequestBody() {
        String username = jiraProperties.getFormAuth().getUsername();
        String password = jiraProperties.getFormAuth().getPassword();
        return String.format("os_username=%s&os_password=%s&os_destination=&user_role=&atl_token=&login=Log+In",
                username, password);

    }

    private ResponseEntity<String> postLoginRequest(String loginUrl, HttpEntity<String> request) {
        try {
            return restTemplate.exchange(loginUrl, HttpMethod.POST, request, String.class);
        } catch (HttpClientErrorException.Unauthorized e) {
            throw new JiraAuthenticationException("Unauthorized, most likely the wrong credentials for basic auth", e);
        }
    }

    private static class LoginResponseParser {
        private static final String UNEXPECTED_ERROR_MESSAGE = "Unexpected error occurred";

        private JiraSession parseLoginResponse(ResponseEntity<String> response) {
            validateLoginResponse(response);
            return getJiraSession(response);
        }

        private JiraSession getJiraSession(ResponseEntity<String> response) {
            List<String> cookies = getCookies(response);
            String sessionId = findSessionId(cookies);
            return new JiraSession(sessionId);
        }

        private List<String> getCookies(ResponseEntity<String> response) {
            List<String> setCookies = response.getHeaders().get("Set-Cookie");
            if (setCookies == null) {
                throw new JiraAuthenticationException("Response on login request does not contain Set-Cookie header");
            }
            return setCookies;
        }

        private String findSessionId(List<String> cookies) {
            return cookies.stream()
                    .filter(cookie -> cookie.startsWith("JSESSIONID"))
                    .map(cookie -> substringBefore(cookie, ";"))
                    .findFirst()
                    .orElseThrow(() -> new JiraAuthenticationException("JSESSIONID not found in Set-Cookie login response"));
        }

        private void validateLoginResponse(ResponseEntity<String> response) {
            if (!response.getStatusCode().is3xxRedirection()) {
                String errorMessage = resolveLoginErrorMessage(response.getBody());
                throw new JiraAuthenticationException("Login failed; error message: " + errorMessage);
            }
        }

        private String resolveLoginErrorMessage(String responseBody) {
            Document document = Jsoup.parse(responseBody);
            Element formBody = document.getElementsByClass("form-body").first();
            if (formBody == null) {
                return UNEXPECTED_ERROR_MESSAGE;
            }
            return formBody.getElementsByClass("aui-message error").text();
        }
    }
}
