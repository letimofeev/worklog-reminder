package com.senla.worklog.reminder.api.jira.v3.client;

import com.senla.worklog.reminder.annotation.RefreshableSession;
import com.senla.worklog.reminder.api.jira.v3.model.WorklogV3;
import com.senla.worklog.reminder.config.JiraProperties;
import com.senla.worklog.reminder.exception.JiraWorklogApiClientException;
import com.senla.worklog.reminder.logging.LogHttpRequest;
import com.senla.worklog.reminder.logging.LogMessageBuilder;
import com.senla.worklog.reminder.service.jira.JiraAuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpMethod.GET;

@Slf4j
@Component
@RequiredArgsConstructor
public class JiraWorklogClientV3Impl implements JiraWorklogClientV3 {
    private final JiraAuthenticationService authenticationService;
    private final RestTemplate restTemplate;
    private final JiraProperties jiraProperties;
    private final LogMessageBuilder logMessageBuilder;

    @Override
    @RefreshableSession
    public List<WorklogV3> getAllForCurrentWeek() {
        return JiraWorklogClientV3.super.getAllForCurrentWeek();
    }

    @Override
    @RefreshableSession
    public List<WorklogV3> getAllForPreviousWeek() {
        return JiraWorklogClientV3.super.getAllForPreviousWeek();
    }

    @Override
    @RefreshableSession
    public List<WorklogV3> getAllForPeriod(LocalDate dateFrom, LocalDate dateTo) {
        try {
            var request = buildRequestEntity();
            var response = makeRequest(dateFrom, dateTo, request);
            return parseResponse(response);
        } catch (Exception e) {
            throw new JiraWorklogApiClientException(e);
        }
    }

    private HttpEntity<WorklogV3[]> buildRequestEntity() {
        var headers = authenticationService.getAuthenticationHeaders();
        return new HttpEntity<>(headers);
    }

    private ResponseEntity<WorklogV3[]> makeRequest(LocalDate dateFrom, LocalDate dateTo, HttpEntity<WorklogV3[]> request) {
        var url = buildUrlTemplate();
        logWorklogsRequest(url, dateFrom, dateTo, request);
        var response = restTemplate.exchange(url, GET, request, WorklogV3[].class, dateFrom, dateTo);
        log.debug("Get worklogs response status: {}", response.getStatusCode());
        return response;
    }

    private String buildUrlTemplate() {
        var url = jiraProperties.getHost() + path;
        return UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("dateFrom", "{dateFrom}")
                .queryParam("dateTo", "{dateTo}")
                .encode()
                .toUriString();
    }

    private List<WorklogV3> parseResponse(ResponseEntity<WorklogV3[]> response) {
        var body = response.getBody();
        requireNonNull(body, "Worklogs from response body must not be null");
        return asList(body);
    }

    private void logWorklogsRequest(String url, LocalDate dateFrom, LocalDate dateTo, HttpEntity<?> request) {
        var logHeader = "Getting worklogs from Jira using Tempo Timesheets V3 Rest:";
        var logMessage = logMessageBuilder.buildRequestLogMessage(logHeader,
                new LogHttpRequest()
                        .setUrl(url)
                        .setMethod(GET)
                        .setParameters(new Object[]{dateFrom, dateTo})
                        .setHeaders(request.getHeaders()));
        log.debug(logMessage);
    }
}
