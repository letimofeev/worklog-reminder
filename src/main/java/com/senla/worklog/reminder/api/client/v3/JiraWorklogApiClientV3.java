package com.senla.worklog.reminder.api.client.v3;

import com.senla.worklog.reminder.api.client.AuthenticatedJiraWorklogApiClient;
import com.senla.worklog.reminder.config.JiraProperties;
import com.senla.worklog.reminder.logging.LogHttpRequest;
import com.senla.worklog.reminder.logging.LogMessageBuilder;
import com.senla.worklog.reminder.model.v3.WorklogV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpMethod.GET;

@Slf4j
//@Component
@RequiredArgsConstructor
public class JiraWorklogApiClientV3 extends AuthenticatedJiraWorklogApiClient {
    private final RestTemplate restTemplate;
    private final JiraProperties jiraProperties;
    private final LogMessageBuilder logMessageBuilder;

    @Override
    protected ResponseEntity<WorklogV3[]> performRequest(LocalDate dateFrom, LocalDate dateTo,
                                                         HttpEntity<?> requestEntity) {
        String url = jiraProperties.getHost() + "/rest/tempo-timesheets/3/worklogs?dateFrom={dateFrom}&dateTo={dateTo}";
        logWorklogsRequest(url, dateFrom, dateTo, requestEntity);
        ResponseEntity<WorklogV3[]> response = restTemplate.exchange(url, GET, requestEntity, WorklogV3[].class, dateFrom, dateTo);
        log.debug("Get worklogs response status: {}", response.getStatusCode());
        return response;
    }

    @Override
    protected List<WorklogV3> parseResponse(ResponseEntity<?> response) {
        WorklogV3[] body = (WorklogV3[]) response.getBody();
        Objects.requireNonNull(body, "Worklogs from response body must not be null");
        return Arrays.asList(body);
    }

    @Override
    protected HttpEntity<?> buildRequestEntity(LocalDate dateFrom, LocalDate dateTo) {
        return new HttpEntity<>(getAuthenticationHeaders());
    }

    private void logWorklogsRequest(String url, LocalDate dateFrom, LocalDate dateTo, HttpEntity<?> request) {
        String logMessage = logMessageBuilder.buildRequestLogMessage("Getting worklogs from Jira Rest API:",
                new LogHttpRequest()
                        .setUrl(url)
                        .setMethod(GET)
                        .setParameters(new Object[]{dateFrom, dateTo})
                        .setHeaders(request.getHeaders()));
        log.debug(logMessage);
    }
}
