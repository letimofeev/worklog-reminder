package com.senla.worklog.reminder.api.client;

import com.senla.worklog.reminder.api.v3.client.JiraWorklogApiClientV3;
import com.senla.worklog.reminder.config.JiraProperties;
import com.senla.worklog.reminder.logging.LogMessageBuilder;
import com.senla.worklog.reminder.api.v3.model.WorklogV3;
import com.senla.worklog.reminder.service.jira.JiraAuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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

class JiraWorklogApiClientV3Test {
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private JiraAuthenticationService authenticationService;

    @Mock
    private LogMessageBuilder logMessageBuilder;

    @Spy
    private JiraProperties jiraProperties = new JiraProperties();

    @InjectMocks
    private JiraWorklogApiClientV3 jiraWorklogApiClient;

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

        String url = "url";

        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(), eq(WorklogV3[].class), eq(dateFrom), eq(dateTo)))
                .thenReturn(new ResponseEntity<>(worklogs, HttpStatus.OK));
        when(authenticationService.getAuthenticationHeaders()).thenReturn(new HttpHeaders());

        List<WorklogV3> expected = Arrays.asList(worklogs);
        List<WorklogV3> actual = jiraWorklogApiClient.getAllForPreviousWeek();

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

        String url = "url";

        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(), eq(WorklogV3[].class), eq(dateFrom), eq(dateTo)))
                .thenReturn(new ResponseEntity<>(worklogs, HttpStatus.OK));

        List<WorklogV3> expected = Arrays.asList(worklogs);
        List<WorklogV3> actual = jiraWorklogApiClient.getAllForCurrentWeek();

        assertEquals(expected, actual);
    }

    @Test
    void getAllForPeriod_shouldReturnExpected_whenInputIsCorrectDates() {
        String url = "some-url";

        LocalDate dateFrom = LocalDate.of(2022, 1, 15);
        LocalDate dateTo = LocalDate.of(2022, 2, 5);

        WorklogV3 worklog1 = new WorklogV3();
        WorklogV3 worklog2 = new WorklogV3();
        worklog1.setId(100L);
        worklog2.setId(200L);
        WorklogV3[] worklogs = {worklog1, worklog2};

        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(), eq(WorklogV3[].class), eq(dateFrom), eq(dateTo)))
                .thenReturn(new ResponseEntity<>(worklogs, HttpStatus.OK));

        List<WorklogV3> expected = Arrays.asList(worklogs);
        List<WorklogV3> actual = jiraWorklogApiClient.getAllForPeriod(dateFrom, dateTo);

        assertEquals(expected, actual);
    }
}
