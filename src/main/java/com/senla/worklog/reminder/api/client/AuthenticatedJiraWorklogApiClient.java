package com.senla.worklog.reminder.api.client;

import com.senla.worklog.reminder.config.JiraProperties;
import com.senla.worklog.reminder.model.Worklog;
import com.senla.worklog.reminder.exception.JiraWorklogApiClientException;
import com.senla.worklog.reminder.service.jira.JiraAuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.util.stream.Collectors.joining;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticatedJiraWorklogApiClient implements JiraWorklogApiClient {
    private final RestTemplate restTemplate;
    private final JiraAuthenticationService authenticationService;
    private final JiraProperties jiraProperties;

    @Override
    public List<Worklog> getAllForPreviousWeek() {
        LocalDate previousMonday = LocalDate.now().with(MONDAY).minusWeeks(1);
        LocalDate previousFriday = LocalDate.now().with(FRIDAY).minusWeeks(1);
        return getAllForPeriod(previousMonday, previousFriday);
    }

    @Override
    public List<Worklog> getAllForCurrentWeek() {
        LocalDate monday = LocalDate.now().with(MONDAY);
        LocalDate friday = LocalDate.now().with(FRIDAY);
        return getAllForPeriod(monday, friday);
    }

    @Override
    public List<Worklog> getAllForPeriod(LocalDate dateFrom, LocalDate dateTo) {
        try {
            HttpHeaders headers = authenticationService.getAuthenticationHeaders();
            HttpEntity<Worklog[]> entity = new HttpEntity<>(headers);
            ResponseEntity<Worklog[]> response = sendGetWorklogsRequest(dateFrom, dateTo, entity);
            return parseResponse(response);
        } catch (Exception e) {
            throw new JiraWorklogApiClientException(e);
        }
    }

    private ResponseEntity<Worklog[]> sendGetWorklogsRequest(LocalDate dateFrom, LocalDate dateTo,
                                                             HttpEntity<Worklog[]> request) {
        String url = jiraProperties.getWorklogsUrlTemplate();
        logGetWorklogsRequest(url, dateFrom, dateTo, request);
        return restTemplate.exchange(url, HttpMethod.GET, request, Worklog[].class, dateFrom, dateTo);
    }

    private List<Worklog> parseResponse(ResponseEntity<Worklog[]> response) {
        Worklog[] body = response.getBody();
        Objects.requireNonNull(body, "Worklogs from response body must not be null");
        return Arrays.asList(body);
    }

    private void logGetWorklogsRequest(String url, LocalDate dateFrom, LocalDate dateTo,
                                       HttpEntity<Worklog[]> request) {
        String headers = request.getHeaders().entrySet().stream()
                .map(entry -> entry.getKey() + ": " + String.join(", ", entry.getValue()))
                .collect(joining(", "));
        log.debug("Getting worklogs from Jira Rest API:\n\t" +
                "Request URL                     " + url + "\n\t" +
                "Request Parameters              " + dateFrom + ", " + dateTo + "\n\t" +
                "Request Method                  " + HttpMethod.GET + "\n\t" +
                "Request Headers                 " + headers + "\n");
    }
}
