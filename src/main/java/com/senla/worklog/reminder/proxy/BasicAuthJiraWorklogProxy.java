package com.senla.worklog.reminder.proxy;

import com.senla.worklog.reminder.config.JiraProperties;
import com.senla.worklog.reminder.exception.JiraWorklogProxyException;
import com.senla.worklog.reminder.dto.WorklogDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.temporal.TemporalAdjusters.previous;

public class BasicAuthJiraWorklogProxy implements JiraWorklogProxy {
    private final RestTemplate restTemplate;
    private final JiraProperties jiraProperties;

    public BasicAuthJiraWorklogProxy(RestTemplate restTemplate, JiraProperties jiraProperties) {
        this.restTemplate = restTemplate;
        this.jiraProperties = jiraProperties;
    }

    @Override
    public List<WorklogDto> findAllForPreviousWeek() {
        LocalDate previousMonday = LocalDate.now().with(previous(MONDAY));
        LocalDate previousFriday = LocalDate.now().with(previous(FRIDAY));
        return findAllForPeriod(previousMonday, previousFriday);
    }

    @Override
    public List<WorklogDto> findAllForCurrentWeek() {
        LocalDate monday = LocalDate.now().with(MONDAY);
        LocalDate friday = LocalDate.now().with(FRIDAY);
        return findAllForPeriod(monday, friday);
    }

    @Override
    public List<WorklogDto> findAllForPeriod(LocalDate dateFrom, LocalDate dateTo) {
        try {
            HttpHeaders headers = buildHeadersWithAuth();
            HttpEntity<WorklogDto[]> entity = new HttpEntity<>(headers);
            String url = jiraProperties.getWorklogsUrlTemplate();
            ResponseEntity<WorklogDto[]> response = restTemplate.exchange(url, HttpMethod.GET,
                    entity, WorklogDto[].class, dateFrom, dateTo);
            return parseResponse(response);
        } catch (Exception e) {
            throw new JiraWorklogProxyException(e);
        }
    }

    private List<WorklogDto> parseResponse(ResponseEntity<WorklogDto[]> response) {
        WorklogDto[] body = response.getBody();
        Objects.requireNonNull(body, "Worklogs from response body must not be null");
        return Arrays.asList(body);
    }

    private HttpHeaders buildHeadersWithAuth() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String encodedCredentials = getEncodedCredentials();
        headers.setBasicAuth(encodedCredentials);
        return headers;
    }

    private String getEncodedCredentials() {
        String username = jiraProperties.getUsername();
        String password = jiraProperties.getPassword();
        byte[] credentials = (username + ":" + password).getBytes();
        return Base64.getEncoder().encodeToString(credentials);
    }
}
