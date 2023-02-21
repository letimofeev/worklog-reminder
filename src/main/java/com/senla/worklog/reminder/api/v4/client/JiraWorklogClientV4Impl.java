package com.senla.worklog.reminder.api.v4.client;

import com.senla.worklog.reminder.annotation.RefreshableSession;
import com.senla.worklog.reminder.api.v4.model.WorklogV4;
import com.senla.worklog.reminder.config.JiraProperties;
import com.senla.worklog.reminder.exception.JiraWorklogApiClientException;
import com.senla.worklog.reminder.logging.LogHttpRequest;
import com.senla.worklog.reminder.logging.LogMessageBuilder;
import com.senla.worklog.reminder.service.jira.JiraAuthenticationService;
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
            HttpEntity<String> request = buildRequestEntity(dateFrom, dateTo);
            ResponseEntity<WorklogV4[]> response = performRequest(dateFrom, dateTo, request);
            return parseResponse(response);
        } catch (Exception e) {
            throw new JiraWorklogApiClientException(e);
        }
    }

    private ResponseEntity<WorklogV4[]> performRequest(LocalDate dateFrom, LocalDate dateTo,
                                                       HttpEntity<String> requestEntity) {
        String url = jiraProperties.getHost() + "/rest/tempo-timesheets/4/worklogs/search";
        logGetWorklogsRequest(url, requestEntity);
        ResponseEntity<WorklogV4[]> response = restTemplate.exchange(url, POST, requestEntity, WorklogV4[].class, dateFrom, dateTo);
        log.debug("Get worklogs response status: {}", response.getStatusCode());
        return response;
    }

    private HttpEntity<String> buildRequestEntity(LocalDate dateFrom, LocalDate dateTo) {
        String body = String.format("{\"from\": \"%s\", \"to\": \"%s\"}", dateFrom, dateTo);
        HttpHeaders headers = authenticationService.getAuthenticationHeaders();
        headers.add("Content-Type", "application/json");
        return new HttpEntity<>(body, headers);
    }

    private List<WorklogV4> parseResponse(ResponseEntity<WorklogV4[]> response) {
        WorklogV4[] body = response.getBody();
        Objects.requireNonNull(body, "Worklogs from response body must not be null");
        return Arrays.asList(body);
    }

    private void logGetWorklogsRequest(String url, HttpEntity<String> request) {
        String logHeader = "Getting worklogs from Jira using Tempo Timesheets V4 Rest:";
        String logMessage = logMessageBuilder.buildRequestLogMessage(logHeader,
                new LogHttpRequest()
                        .setUrl(url)
                        .setMethod(POST)
                        .setBody(request.getBody())
                        .setHeaders(request.getHeaders()));
        log.debug(logMessage);
    }
}
