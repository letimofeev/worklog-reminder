package com.senla.worklog.reminder.service.jira;

import com.senla.worklog.reminder.config.JiraProperties;
import com.senla.worklog.reminder.exception.JiraAuthenticationException;
import com.senla.worklog.reminder.logging.LogHttpRequest;
import com.senla.worklog.reminder.logging.LogMessageBuilder;
import com.senla.worklog.reminder.model.JiraSession;
import com.senla.worklog.reminder.repository.JiraSessionStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.POST;

@Slf4j
@Service
@RequiredArgsConstructor
public class JiraAuthenticationServiceImpl implements JiraAuthenticationService {
    private final RestTemplate restTemplate;
    private final JiraSessionStorage sessionStorage;
    private final LoginResponseParser loginResponseParser;
    private final JiraProperties jiraProperties;
    private final LogMessageBuilder logMessageBuilder;

    @Override
    public JiraSession login() {
        try {
            var loginUrl = jiraProperties.getLoginUrl();
            var request = getLoginRequest();
            var response = postLoginRequest(loginUrl, request);
            var session = loginResponseParser.parseLoginResponse(response);
            sessionStorage.saveSession(session);
            return session;
        } catch (Exception e) {
            throw new JiraAuthenticationException("Exception during logging in Jira; nested exception: " + e, e);
        }
    }

    @Override
    public JiraSession getSession() {
        return getSessionInternal();
    }

    @Override
    public HttpHeaders getAuthenticationHeaders() {
        var headers = new HttpHeaders();
        addBasicAuthHeader(headers);
        addSessionIdHeader(headers);
        return headers;
    }

    private HttpEntity<String> getLoginRequest() {
        var headers = getLoginRequestHeaders();
        var requestBody = getLoginRequestBody();
        return new HttpEntity<>(requestBody, headers);
    }

    private HttpHeaders getLoginRequestHeaders() {
        var headers = new HttpHeaders();
        addBasicAuthHeader(headers);
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        return headers;
    }

    private void addBasicAuthHeader(HttpHeaders headers) {
        var username = jiraProperties.getBasicAuth().getUsername();
        var password = jiraProperties.getBasicAuth().getPassword();
        headers.setBasicAuth(username, password);
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
        var username = jiraProperties.getFormAuth().getUsername();
        var password = jiraProperties.getFormAuth().getPassword();
        return String.format("os_username=%s&os_password=%s&os_destination=&user_role=&atl_token=&login=Log+In",
                username, password);

    }

    private ResponseEntity<String> postLoginRequest(String loginUrl, HttpEntity<String> request) {
        logLoginRequest(loginUrl, request);
        try {
            var response = restTemplate.exchange(loginUrl, POST, request, String.class);
            log.debug("Logging in Jira response status: {}", response.getStatusCode());
            return response;
        } catch (HttpClientErrorException.Unauthorized e) {
            throw new JiraAuthenticationException("Unauthorized, most likely the wrong credentials for basic auth", e);
        }
    }

    private void logLoginRequest(String loginUrl, HttpEntity<String> request) {
        var logMessage = logMessageBuilder.buildRequestLogMessage("Logging in Jira with the following data:",
                new LogHttpRequest()
                        .setUrl(loginUrl)
                        .setMethod(POST)
                        .setHeaders(request.getHeaders())
                        .setBody(request.getBody()));
        log.debug(logMessage);
    }
}
