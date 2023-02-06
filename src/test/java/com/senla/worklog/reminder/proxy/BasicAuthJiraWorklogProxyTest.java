package com.senla.worklog.reminder.proxy;

import com.senla.worklog.reminder.config.JiraProperties;
import com.senla.worklog.reminder.dto.WorklogDto;
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
        LocalDate dateFrom = LocalDate.now().with(MONDAY).minusWeeks(1);
        LocalDate dateTo = LocalDate.now().with(FRIDAY).minusWeeks(1);

        WorklogDto worklog1 = new WorklogDto();
        WorklogDto worklog2 = new WorklogDto();
        worklog1.setId(100L);
        worklog2.setId(200L);
        WorklogDto[] worklogs = {worklog1, worklog2};

        String url = jiraProperties.getWorklogsUrlTemplate();

        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(), eq(WorklogDto[].class), eq(dateFrom), eq(dateTo)))
                .thenReturn(new ResponseEntity<>(worklogs, HttpStatus.OK));

        List<WorklogDto> expected = Arrays.asList(worklogs);
        List<WorklogDto> actual = jiraWorklogProxy.findAllForPreviousWeek();

        assertEquals(expected, actual);
    }

    @Test
    void findAllForCurrentWeek_shouldReturnExpectedForMondayFriday_whenInputIsNoArgs() {
        LocalDate dateFrom = LocalDate.now().with(MONDAY);
        LocalDate dateTo = LocalDate.now().with(FRIDAY);

        WorklogDto worklog1 = new WorklogDto();
        WorklogDto worklog2 = new WorklogDto();
        worklog1.setId(120L);
        worklog2.setId(210L);
        WorklogDto[] worklogs = {worklog1, worklog2};

        String url = jiraProperties.getWorklogsUrlTemplate();

        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(), eq(WorklogDto[].class), eq(dateFrom), eq(dateTo)))
                .thenReturn(new ResponseEntity<>(worklogs, HttpStatus.OK));

        List<WorklogDto> expected = Arrays.asList(worklogs);
        List<WorklogDto> actual = jiraWorklogProxy.findAllForCurrentWeek();

        assertEquals(expected, actual);
    }

    @Test
    void findAllForPeriod_shouldReturnExpected_whenInputIsCorrectDates() {
        String url = jiraProperties.getWorklogsUrlTemplate();

        LocalDate dateFrom = LocalDate.of(2022, 1, 15);
        LocalDate dateTo = LocalDate.of(2022, 2, 5);

        WorklogDto worklog1 = new WorklogDto();
        WorklogDto worklog2 = new WorklogDto();
        worklog1.setId(100L);
        worklog2.setId(200L);
        WorklogDto[] worklogs = {worklog1, worklog2};

        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(), eq(WorklogDto[].class), eq(dateFrom), eq(dateTo)))
                .thenReturn(new ResponseEntity<>(worklogs, HttpStatus.OK));

        List<WorklogDto> expected = Arrays.asList(worklogs);
        List<WorklogDto> actual = jiraWorklogProxy.findAllForPeriod(dateFrom, dateTo);

        assertEquals(expected, actual);
    }
}
