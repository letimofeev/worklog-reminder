package com.senla.worklog.reminder.api.client;

import com.senla.worklog.reminder.config.JiraProperties;
import com.senla.worklog.reminder.exception.JiraAuthenticationException;
import com.senla.worklog.reminder.exception.JiraWorklogApiClientException;
import com.senla.worklog.reminder.model.Worklog;
import com.senla.worklog.reminder.service.jira.JiraAuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.lang.String.join;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpMethod.GET;

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
            HttpEntity<Worklog[]> request = new HttpEntity<>(headers);
            ResponseEntity<Worklog[]> response = sendGetWorklogsRequest(dateFrom, dateTo, request);
            return parseResponse(response);
        } catch (Exception e) {
            throw new JiraWorklogApiClientException(e);
        }
    }

    private ResponseEntity<Worklog[]> sendGetWorklogsRequest(LocalDate dateFrom, LocalDate dateTo, HttpEntity<Worklog[]> request) {
        return sendGetWorklogsRequest(dateFrom, dateTo, request, false);
    }

    private ResponseEntity<Worklog[]> sendGetWorklogsRequest(LocalDate dateFrom, LocalDate dateTo,
                                                             HttpEntity<Worklog[]> request, boolean isRetry) {
        String url = jiraProperties.getWorklogsUrlTemplate();
        logGetWorklogsRequest(url, dateFrom, dateTo, request);
        try {
            ResponseEntity<Worklog[]> response = restTemplate.exchange(url, GET, request, Worklog[].class, dateFrom, dateTo);
            log.debug("Get worklogs response status: {}", response.getStatusCode());
            return response;
        } catch (HttpClientErrorException.Unauthorized e) {
            return handleUnauthorizedInternal(dateFrom, dateTo, request, isRetry);
        }
    }

    private ResponseEntity<Worklog[]> handleUnauthorizedInternal(LocalDate dateFrom, LocalDate dateTo,
                                                                 HttpEntity<Worklog[]> request, boolean isRetry) {
        if (isRetry) {
            log.error("Received 401 Unauthorized after retry with login");
            throw new JiraAuthenticationException("Jira Api Client 401 Unauthorized after retry with login. " +
                    "Make sure that request was sent with correct headers");
        }
        log.warn("Received 401 Unauthorized. Perhaps session is expired, logging in and trying again");
        authenticationService.login();
        return sendGetWorklogsRequest(dateFrom, dateTo, request, true);
    }

    private List<Worklog> parseResponse(ResponseEntity<Worklog[]> response) {
        Worklog[] body = response.getBody();
        Objects.requireNonNull(body, "Worklogs from response body must not be null");
        return Arrays.asList(body);
    }

    private void logGetWorklogsRequest(String url, LocalDate dateFrom, LocalDate dateTo, HttpEntity<Worklog[]> request) {
        String headers = request.getHeaders().entrySet().stream()
                .map(entry -> entry.getKey() + ": " + join(", ", entry.getValue()))
                .collect(joining(", "));
        log.debug("Getting worklogs from Jira Rest API:\n\t" +
                "Request URL Template            " + url + "\n\t" +
                "Request Parameters              " + dateFrom + ", " + dateTo + "\n\t" +
                "Request Method                  " + GET + "\n\t" +
                "Request Headers                 " + headers + "\n");
    }
}
