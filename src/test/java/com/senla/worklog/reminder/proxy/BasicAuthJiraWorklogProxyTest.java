package com.senla.worklog.reminder.proxy;

import com.senla.worklog.reminder.config.JiraProperties;
import com.senla.worklog.reminder.model.Worklog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.temporal.TemporalAdjusters.previous;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class BasicAuthJiraWorklogProxyTest {
    @Mock
    private RestTemplate restTemplate;

    @Spy
    private JiraProperties jiraProperties = new JiraProperties("http://host/worklogs?dateFrom={dateFrom}&dateTo={dateTo}",
            "username", "password");

    @InjectMocks
    private BasicAuthJiraWorklogProxy jiraWorklogProxy;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void findAllForPreviousWeek_shouldReturnExpectedForPrevMondayFriday_whenInputIsNoArgs() {
        LocalDate dateFrom = LocalDate.now().with(previous(MONDAY));
        LocalDate dateTo = LocalDate.now().with(previous(FRIDAY));

        Worklog worklog1 = new Worklog();
        Worklog worklog2 = new Worklog();
        worklog1.setId(100L);
        worklog2.setId(200L);
        Worklog[] worklogs = {worklog1, worklog2};

        String url = jiraProperties.getWorklogsUrlTemplate();

        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(), eq(Worklog[].class), eq(dateFrom), eq(dateTo)))
                .thenReturn(new ResponseEntity<>(worklogs, HttpStatus.OK));

        List<Worklog> expected = Arrays.asList(worklogs);
        List<Worklog> actual = jiraWorklogProxy.findAllForPreviousWeek();

        assertEquals(expected, actual);
    }

    @Test
    void findAllForPeriod_shouldReturnExpected_whenInputIsCorrectDates() {
        String url = jiraProperties.getWorklogsUrlTemplate();

        LocalDate dateFrom = LocalDate.of(2022, 1, 15);
        LocalDate dateTo = LocalDate.of(2022, 2, 5);

        Worklog worklog1 = new Worklog();
        Worklog worklog2 = new Worklog();
        worklog1.setId(100L);
        worklog2.setId(200L);
        Worklog[] worklogs = {worklog1, worklog2};

        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(), eq(Worklog[].class), eq(dateFrom), eq(dateTo)))
                .thenReturn(new ResponseEntity<>(worklogs, HttpStatus.OK));

        List<Worklog> expected = Arrays.asList(worklogs);
        List<Worklog> actual = jiraWorklogProxy.findAllForPeriod(dateFrom, dateTo);

        assertEquals(expected, actual);
    }
}