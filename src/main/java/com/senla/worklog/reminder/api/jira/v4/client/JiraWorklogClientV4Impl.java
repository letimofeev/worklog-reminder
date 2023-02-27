package com.senla.worklog.reminder.api.jira.v4.client;

import com.senla.worklog.reminder.annotation.RefreshableSession;
import com.senla.worklog.reminder.api.jira.v4.model.WorklogV4;
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

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpMethod.POST;

@Slf4j
@Component
@RequiredArgsConstructor
public class JiraWorklogClientV4Impl implements JiraWorklogClientV4 {
    private final JiraAuthenticationService authenticationService;
    private final RestTemplate restTemplate;
    private final JiraProperties jiraProperties;
    private final LogMessageBuilder logMessageBuilder;

    @Override
    @RefreshableSession
    public List<WorklogV4> getAllForCurrentWeek() {
        return JiraWorklogClientV4.super.getAllForCurrentWeek();
    }

    @Override
    @RefreshableSession
    public List<WorklogV4> getAllForPreviousWeek() {
        return JiraWorklogClientV4.super.getAllForPreviousWeek();
    }

    @Override
    @RefreshableSession
    public List<WorklogV4> getAllForPeriod(LocalDate dateFrom, LocalDate dateTo) {
        try {
            var request = buildRequestEntity(dateFrom, dateTo);
            var response = makeRequest(dateFrom, dateTo, request);
            return parseResponse(response);
        } catch (Exception e) {
            throw new JiraWorklogApiClientException(e);
        }
    }

    private ResponseEntity<WorklogV4[]> makeRequest(LocalDate dateFrom, LocalDate dateTo, HttpEntity<String> request) {
        var url = jiraProperties.getHost() + path;
        logGetWorklogsRequest(url, request);
        var response = restTemplate.exchange(url, POST, request, WorklogV4[].class, dateFrom, dateTo);
        log.debug("Get worklogs response status: {}", response.getStatusCode());
        return response;
    }

    private HttpEntity<String> buildRequestEntity(LocalDate dateFrom, LocalDate dateTo) {
        var body = String.format("{\"from\": \"%s\", \"to\": \"%s\"}", dateFrom, dateTo);
        var headers = authenticationService.getAuthenticationHeaders();
        headers.add("Content-Type", "application/json");
        return new HttpEntity<>(body, headers);
    }

    private List<WorklogV4> parseResponse(ResponseEntity<WorklogV4[]> response) {
        var body = response.getBody();
        requireNonNull(body, "Worklogs from response body must not be null");
        return asList(body);
    }

    private void logGetWorklogsRequest(String url, HttpEntity<String> request) {
        var logHeader = "Getting worklogs from Jira using Tempo Timesheets V4 Rest:";
        var logMessage = logMessageBuilder.buildRequestLogMessage(logHeader,
                new LogHttpRequest()
                        .setUrl(url)
                        .setMethod(POST)
                        .setBody(request.getBody())
                        .setHeaders(request.getHeaders()));
        log.debug(logMessage);
    }
}
