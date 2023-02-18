package com.senla.worklog.reminder.service;

import com.senla.worklog.reminder.config.JiraProperties;
import com.senla.worklog.reminder.model.JiraSession;
import com.senla.worklog.reminder.repository.JiraSessionStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class JiraAuthenticationServiceImpl implements JiraAuthenticationService {
    private final RestTemplate restTemplate;
    private final JiraSessionStorage sessionStorage;
    private final JiraProperties jiraProperties;

    @Override
    public JiraSession login() {
        String username = jiraProperties.getFormAuth().getUsername();
        String password = jiraProperties.getFormAuth().getPassword();
        String loginUrl = jiraProperties.getLoginUrl();

        HttpHeaders headers = new HttpHeaders();
        addBasicAuthHeader(headers);
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        String requestBody = String.format("os_username=%s&os_password=%s&os_destination=&user_role=&atl_token=&login=Log+In", username, password);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(loginUrl, HttpMethod.POST, entity, String.class);

        String sessionId = response.getHeaders().get("Set-Cookie").stream()
                .filter(header -> header.startsWith("JSESSIONID"))
                .map(cookie -> cookie.split(";")[0])
                .findFirst()
                .get();
        JiraSession session = new JiraSession(sessionId);
        sessionStorage.addSession(session);
        return session;
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
