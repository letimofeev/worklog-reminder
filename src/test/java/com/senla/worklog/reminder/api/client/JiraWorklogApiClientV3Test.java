package com.senla.worklog.reminder.api.client;

import com.senla.worklog.reminder.api.v3.client.JiraWorklogClientV3Impl;
import com.senla.worklog.reminder.api.v3.model.WorklogV3;
import com.senla.worklog.reminder.config.JiraProperties;
import com.senla.worklog.reminder.logging.LogMessageBuilder;
import com.senla.worklog.reminder.service.jira.JiraAuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

class JiraWorklogApiClientV3Test {
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private JiraAuthenticationService authenticationService;

    @Mock
    private LogMessageBuilder logMessageBuilder;

    @Spy
    private JiraProperties jiraProperties = new JiraProperties().setHost("https://someurl.com");

    @InjectMocks
    private JiraWorklogClientV3Impl jiraWorklogClientV3;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void getAllForPreviousWeek_shouldReturnExpectedForPrevMondayFriday_whenInputIsNoArgs() {
        LocalDate dateFrom = LocalDate.now().with(MONDAY).minusWeeks(1);
        LocalDate dateTo = LocalDate.now().with(FRIDAY).minusWeeks(1);

        WorklogV3 worklog1 = new WorklogV3();
        WorklogV3 worklog2 = new WorklogV3();
        worklog1.setId(100L);
        worklog2.setId(200L);
        WorklogV3[] worklogs = {worklog1, worklog2};

        String url = jiraProperties.getHost() + "/rest/tempo-timesheets/3/worklogs?dateFrom={dateFrom}&dateTo={dateTo}";

        when(restTemplate.exchange(eq(url), eq(GET), any(), eq(WorklogV3[].class), eq(dateFrom), eq(dateTo)))
                .thenReturn(new ResponseEntity<>(worklogs, OK));
        when(authenticationService.getAuthenticationHeaders()).thenReturn(new HttpHeaders());

        List<WorklogV3> expected = Arrays.asList(worklogs);
        List<WorklogV3> actual = jiraWorklogClientV3.getAllForPreviousWeek();

        assertEquals(expected, actual);
    }

    @Test
    void getAllForCurrentWeek_shouldReturnExpectedForMondayFriday_whenInputIsNoArgs() {
        LocalDate dateFrom = LocalDate.now().with(MONDAY);
        LocalDate dateTo = LocalDate.now().with(FRIDAY);

        WorklogV3 worklog1 = new WorklogV3();
        WorklogV3 worklog2 = new WorklogV3();
        worklog1.setId(120L);
        worklog2.setId(210L);
        WorklogV3[] worklogs = {worklog1, worklog2};

        String url = jiraProperties.getHost() + "/rest/tempo-timesheets/3/worklogs?dateFrom={dateFrom}&dateTo={dateTo}";

        when(restTemplate.exchange(eq(url), eq(GET), any(), eq(WorklogV3[].class), eq(dateFrom), eq(dateTo)))
                .thenReturn(new ResponseEntity<>(worklogs, OK));

        List<WorklogV3> expected = Arrays.asList(worklogs);
        List<WorklogV3> actual = jiraWorklogClientV3.getAllForCurrentWeek();

        assertEquals(expected, actual);
    }

    @Test
    void getAllForPeriod_shouldReturnExpected_whenInputIsCorrectDates() {
        LocalDate dateFrom = LocalDate.of(2022, 1, 15);
        LocalDate dateTo = LocalDate.of(2022, 2, 5);

        WorklogV3 worklog1 = new WorklogV3();
        WorklogV3 worklog2 = new WorklogV3();
        worklog1.setId(100L);
        worklog2.setId(200L);
        WorklogV3[] worklogs = {worklog1, worklog2};

        String url = jiraProperties.getHost() + "/rest/tempo-timesheets/3/worklogs?dateFrom={dateFrom}&dateTo={dateTo}";

        when(restTemplate.exchange(eq(url), eq(GET), any(), eq(WorklogV3[].class), eq(dateFrom), eq(dateTo)))
                .thenReturn(new ResponseEntity<>(worklogs, OK));

        List<WorklogV3> expected = Arrays.asList(worklogs);
        List<WorklogV3> actual = jiraWorklogClientV3.getAllForPeriod(dateFrom, dateTo);

        assertEquals(expected, actual);
    }
}
