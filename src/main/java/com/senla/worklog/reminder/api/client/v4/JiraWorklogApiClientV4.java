package com.senla.worklog.reminder.api.client.v4;

import com.senla.worklog.reminder.api.client.AuthenticatedJiraWorklogApiClient;
import com.senla.worklog.reminder.config.JiraProperties;
import com.senla.worklog.reminder.logging.LogHttpRequest;
import com.senla.worklog.reminder.logging.LogMessageBuilder;
import com.senla.worklog.reminder.model.mapper.WorklogMapper;
import com.senla.worklog.reminder.model.v3.WorklogV3;
import com.senla.worklog.reminder.model.v4.WorklogV4;
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
    private final WorklogMapper worklogMapper;

    @Override
    protected ResponseEntity<WorklogV3[]> performRequest(LocalDate dateFrom, LocalDate dateTo, HttpEntity<?> requestEntity) {
        String url = jiraProperties.getHost() + "/rest/tempo-timesheets/4/worklogs/search";
        logGetWorklogsRequest(url, requestEntity);
        ResponseEntity<WorklogV4[]> response = restTemplate.exchange(url, POST, requestEntity, WorklogV4[].class, dateFrom, dateTo);
        log.debug("Get worklogs response status: {}", response.getStatusCode());
        WorklogV3[] worklogs = Arrays.stream(response.getBody()).map(worklogMapper::mapToV3).toArray(WorklogV3[]::new);
        return new ResponseEntity<>(worklogs, response.getStatusCode());
    }

    @Override
    protected HttpEntity<?> buildRequestEntity(LocalDate dateFrom, LocalDate dateTo) {
        String body = String.format("{\"from\": \"%s\", \"to\": \"%s\"}", dateFrom, dateTo);
        HttpHeaders headers = getAuthenticationHeaders();
        headers.add("Content-Type", "application/json");
        return new HttpEntity<>(body, headers);
    }

    @Override
    protected List<WorklogV3> parseResponse(ResponseEntity<?> response) {
        WorklogV3[] body = (WorklogV3[]) response.getBody();
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
