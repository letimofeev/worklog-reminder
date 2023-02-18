package com.senla.worklog.reminder.proxy;

import com.senla.worklog.reminder.config.JiraProperties;
import com.senla.worklog.reminder.model.Worklog;
import com.senla.worklog.reminder.exception.JiraWorklogProxyException;
import com.senla.worklog.reminder.service.JiraSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;

@Component
@RequiredArgsConstructor
public class BasicAuthJiraWorklogProxy implements JiraWorklogProxy {
    private final RestTemplate restTemplate;
    private final JiraSessionService sessionService;
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
            HttpHeaders headers = buildHeadersWithAuth();
            HttpEntity<Worklog[]> entity = new HttpEntity<>(headers);
            String url = jiraProperties.getWorklogsUrlTemplate();
            ResponseEntity<Worklog[]> response = restTemplate.exchange(url, HttpMethod.GET,
                    entity, Worklog[].class, dateFrom, dateTo);
            return parseResponse(response);
        } catch (Exception e) {
            throw new JiraWorklogProxyException(e);
        }
    }

    private List<Worklog> parseResponse(ResponseEntity<Worklog[]> response) {
        Worklog[] body = response.getBody();
        Objects.requireNonNull(body, "Worklogs from response body must not be null");
        return Arrays.asList(body);
    }

    private HttpHeaders buildHeadersWithAuth() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String username = jiraProperties.getBasicAuth().getUsername();
        String password = jiraProperties.getBasicAuth().getPassword();
        headers.setBasicAuth(username, password);
        headers.set("Cookie", sessionService.getSession().getSessionId());
        return headers;
    }
}
