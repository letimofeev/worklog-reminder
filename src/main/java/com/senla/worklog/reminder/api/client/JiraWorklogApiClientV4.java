package com.senla.worklog.reminder.api.client;

import com.senla.worklog.reminder.config.JiraProperties;
import com.senla.worklog.reminder.logging.LogHttpRequest;
import com.senla.worklog.reminder.logging.LogMessageBuilder;
import com.senla.worklog.reminder.model.Worklog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpMethod.POST;

@Slf4j
@Component
@RequiredArgsConstructor
public class JiraWorklogApiClientV4 extends AuthenticatedJiraWorklogApiClient {
    private final RestTemplate restTemplate;
    private final JiraProperties jiraProperties;
    private final LogMessageBuilder logMessageBuilder;

    @Override
    protected ResponseEntity<Worklog[]> performRequest(LocalDate dateFrom, LocalDate dateTo, HttpEntity<?> requestEntity) {
        String url = jiraProperties.getHost() + "/rest/tempo-timesheets/4/worklogs/search";
        logGetWorklogsRequest(url, requestEntity);
        ResponseEntity<Worklog[]> response = restTemplate.exchange(url, POST, requestEntity, Worklog[].class, dateFrom, dateTo);
        log.debug("Get worklogs response status: {}", response.getStatusCode());
        return response;
    }

    @Override
    protected HttpEntity<?> buildRequestEntity(LocalDate dateFrom, LocalDate dateTo) {
        String body = String.format("{\"from\": \"%s\", \"to\": \"%s\"}", dateFrom, dateTo);
        HttpHeaders headers = getAuthenticationHeaders();
        headers.add("Content-Type", "application/json");
        return new HttpEntity<>(body, headers);
    }

    @Override
    protected List<Worklog> parseResponse(ResponseEntity<?> response) {
        Worklog[] body = (Worklog[]) response.getBody();
        Objects.requireNonNull(body, "Worklogs from response body must not be null");
        return Arrays.asList(body);
    }


    private void logGetWorklogsRequest(String url, HttpEntity<?> request) {
        String logMessage = logMessageBuilder.buildRequestLogMessage("Getting worklogs from Jira Rest API:",
                new LogHttpRequest()
                        .setUrl(url)
                        .setMethod(POST)
                        .setBody(String.valueOf(request.getBody()))
                        .setHeaders(request.getHeaders()));
        log.debug(logMessage);
    }
}
