package com.senla.worklog.reminder.service;

import com.senla.worklog.reminder.config.JiraProperties;
import com.senla.worklog.reminder.exception.JiraAuthenticationException;
import com.senla.worklog.reminder.model.JiraSession;
import com.senla.worklog.reminder.repository.JiraSessionStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static liquibase.repackaged.org.apache.commons.lang3.StringUtils.substringBefore;

@Service
@RequiredArgsConstructor
public class JiraAuthenticationServiceImpl implements JiraAuthenticationService {
    private final RestTemplate restTemplate;
    private final JiraSessionStorage sessionStorage;
    private final JiraProperties jiraProperties;

    @Override
    public JiraSession login() {
        try {
            String loginUrl = jiraProperties.getLoginUrl();
            HttpEntity<String> request = getLoginRequest();
            ResponseEntity<String> response = restTemplate.exchange(loginUrl, HttpMethod.POST, request, String.class);
            JiraSession session = parseLoginResponse(response);
            sessionStorage.addSession(session);
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

    private String getLoginRequestBody() {
        String username = jiraProperties.getFormAuth().getUsername();
        String password = jiraProperties.getFormAuth().getPassword();
        return String.format("os_username=%s&os_password=%s&os_destination=&user_role=&atl_token=&login=Log+In",
                username, password);

    }

    private JiraSession parseLoginResponse(ResponseEntity<String> response) {
        List<String> setCookies = response.getHeaders().get("Set-Cookie");
        if (setCookies == null) {
            throw new JiraAuthenticationException("Response on login request does not contain Set-Cookie header");
        }
        String sessionId = setCookies.stream()
                .filter(cookie -> cookie.startsWith("JSESSIONID"))
                .map(cookie -> substringBefore(cookie, ";"))
                .findFirst()
                .orElseThrow(() -> new JiraAuthenticationException("JSESSIONID not found in Set-Cookie login response"));
        return new JiraSession(sessionId);
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
        return sessionStorage.getSession().orElseGet(this::login);
    }
}
