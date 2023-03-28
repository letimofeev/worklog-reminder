package com.senla.worklog.reminder.worklogdebt.adapter.out.rest.jira;

import com.senla.worklog.reminder.annotation.DrivenAdapter;
import com.senla.worklog.reminder.worklogdebt.adapter.out.rest.jira.auth.JiraAuthenticationService;
import com.senla.worklog.reminder.worklogdebt.adapter.out.rest.jira.auth.RefreshableSession;
import com.senla.worklog.reminder.worklogdebt.adapter.out.rest.jira.dto.WorklogDto;
import com.senla.worklog.reminder.worklogdebt.adapter.out.rest.jira.logging.HttpRequestLog;
import com.senla.worklog.reminder.worklogdebt.adapter.out.rest.jira.logging.LogMessageBuilder;
import com.senla.worklog.reminder.worklogdebt.adapter.out.rest.jira.mapper.JiraRestWorklogMapper;
import com.senla.worklog.reminder.worklogdebt.domain.model.Worklog;
import com.senla.worklog.reminder.worklogdebt.domain.port.out.JiraRestPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpMethod.POST;

@Slf4j
@DrivenAdapter
@RequiredArgsConstructor
public class JiraRestAdapter implements JiraRestPort {
    private final JiraAuthenticationService authenticationService;
    private final JiraRestWorklogMapper restMapper;
    private final JiraRestProperties restProperties;
    private final RestTemplate restTemplate;
    private final LogMessageBuilder logMessageBuilder;

    @Override
    @RefreshableSession
    public List<Worklog> getJiraWorklogs(LocalDate dateFrom, LocalDate dateTo) {
        var request = buildRequestEntity(dateFrom, dateTo);
        var response = performRequest(dateFrom, dateTo, request);
        return parseResponse(response).stream()
                .map(restMapper::mapToDomain)
                .collect(toList());
    }

    @Override
    @RefreshableSession
    public List<String> getJiraWorkersKeys(LocalDate dateFrom, LocalDate dateTo) {
        return getJiraWorklogs(dateFrom, dateTo).stream()
                .map(Worklog::getWorkerJiraKey)
                .collect(toList());
    }

    private ResponseEntity<WorklogDto[]> performRequest(LocalDate dateFrom, LocalDate dateTo, HttpEntity<String> request) {
        var uri = restProperties.getV4GetWorklogDebtsUri();
        logGetWorklogsRequest(uri, request);
        var response = restTemplate.exchange(uri, POST, request, WorklogDto[].class, dateFrom, dateTo);
        log.debug("Get worklogs response status: {}", response.getStatusCode());
        return response;
    }

    private HttpEntity<String> buildRequestEntity(LocalDate dateFrom, LocalDate dateTo) {
        var body = String.format("{\"from\": \"%s\", \"to\": \"%s\"}", dateFrom, dateTo);
        var headers = authenticationService.getAuthenticationHeaders();
        headers.add("Content-Type", "application/json");
        return new HttpEntity<>(body, headers);
    }

    private List<WorklogDto> parseResponse(ResponseEntity<WorklogDto[]> response) {
        var body = response.getBody();
        requireNonNull(body, "Worklogs from response body must not be null");
        return asList(body);
    }

    private void logGetWorklogsRequest(String url, HttpEntity<String> request) {
        var logHeader = "Getting worklogs from Jira using Tempo Timesheets V4 Rest:";
        var logMessage = logMessageBuilder.buildRequestLogMessage(logHeader,
                new HttpRequestLog()
                        .setUrl(url)
                        .setMethod(POST)
                        .setBody(request.getBody())
                        .setHeaders(request.getHeaders()));
        log.debug(logMessage);
    }
}
