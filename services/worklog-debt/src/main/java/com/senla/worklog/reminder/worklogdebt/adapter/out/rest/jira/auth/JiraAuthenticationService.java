package com.senla.worklog.reminder.worklogdebt.adapter.out.rest.jira.auth;

import com.senla.worklog.reminder.worklogdebt.adapter.out.rest.jira.JiraRestProperties;
import com.senla.worklog.reminder.worklogdebt.adapter.out.rest.jira.auth.session.JiraSession;
import com.senla.worklog.reminder.worklogdebt.adapter.out.rest.jira.auth.session.JiraSessionStorage;
import com.senla.worklog.reminder.worklogdebt.adapter.out.rest.jira.logging.HttpRequestLog;
import com.senla.worklog.reminder.worklogdebt.adapter.out.rest.jira.logging.LogMessageBuilder;
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
public class JiraAuthenticationService {
    private final RestTemplate restTemplate;
    private final JiraSessionStorage sessionStorage;
    private final LoginResponseParser loginResponseParser;
    private final JiraRestProperties jiraProperties;
    private final LogMessageBuilder logMessageBuilder;

    public JiraSession login() {
        try {
            var loginUrl = jiraProperties.getLoginUrl();
            var request = buildLoginRequest();
            var response = postLoginRequest(loginUrl, request);
            var session = loginResponseParser.parseLoginResponse(response);
            sessionStorage.saveSession(session);
            return session;
        } catch (Exception e) {
            throw new JiraAuthenticationException("Exception during logging in Jira; nested exception: " + e, e);
        }
    }

    public HttpHeaders getAuthenticationHeaders() {
        var headers = new HttpHeaders();
        addBasicAuthHeader(headers);
        addSessionIdHeader(headers);
        return headers;
    }

    private HttpEntity<String> buildLoginRequest() {
        var headers = buildLoginRequestHeaders();
        var requestBody = buildLoginRequestBody();
        return new HttpEntity<>(requestBody, headers);
    }

    private HttpHeaders buildLoginRequestHeaders() {
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

    private String buildLoginRequestBody() {
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
                new HttpRequestLog()
                        .setUrl(loginUrl)
                        .setMethod(POST)
                        .setHeaders(request.getHeaders())
                        .setBody(request.getBody()));
        log.debug(logMessage);
    }
}
