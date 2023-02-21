package com.senla.worklog.reminder.api.client;

import com.senla.worklog.reminder.annotation.RefreshableSession;
import com.senla.worklog.reminder.exception.JiraWorklogApiClientException;
import com.senla.worklog.reminder.model.Worklog;
import com.senla.worklog.reminder.service.jira.JiraAuthenticationService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;

@Getter
public abstract class AuthenticatedJiraWorklogApiClient implements JiraWorklogApiClient {
    private JiraAuthenticationService authenticationService;

    @Autowired
    public void setAuthenticationService(JiraAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    @RefreshableSession
    public List<Worklog> getAllForPreviousWeek() {
        LocalDate previousMonday = LocalDate.now().with(MONDAY).minusWeeks(1);
        LocalDate previousFriday = LocalDate.now().with(FRIDAY).minusWeeks(1);
        return getAllForPeriod(previousMonday, previousFriday);
    }

    @Override
    @RefreshableSession
    public List<Worklog> getAllForCurrentWeek() {
        LocalDate monday = LocalDate.now().with(MONDAY);
        LocalDate friday = LocalDate.now().with(FRIDAY);
        return getAllForPeriod(monday, friday);
    }

    @Override
    @RefreshableSession
    public List<Worklog> getAllForPeriod(LocalDate dateFrom, LocalDate dateTo) {
        try {
            HttpEntity<?> request = buildRequestEntity(dateFrom, dateTo);
            ResponseEntity<?> response = performRequest(dateFrom, dateTo, request);
            return parseResponse(response);
        } catch (Exception e) {
            throw new JiraWorklogApiClientException(e);
        }
    }

    protected HttpHeaders getAuthenticationHeaders() {
        return authenticationService.getAuthenticationHeaders();
    }

    protected abstract HttpEntity<?> buildRequestEntity(LocalDate dateFrom, LocalDate dateTo);

    protected abstract ResponseEntity<?> performRequest(LocalDate dateFrom, LocalDate dateTo, HttpEntity<?> requestEntity);

    protected abstract List<Worklog> parseResponse(ResponseEntity<?> responseEntity);
}
