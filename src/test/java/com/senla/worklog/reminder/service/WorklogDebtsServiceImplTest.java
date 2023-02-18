package com.senla.worklog.reminder.service;

import com.senla.worklog.reminder.dto.EmployeeDto;
import com.senla.worklog.reminder.model.Author;
import com.senla.worklog.reminder.model.DayWorklogDebt;
import com.senla.worklog.reminder.model.Worklog;
import com.senla.worklog.reminder.model.WorklogDebts;
import com.senla.worklog.reminder.api.client.JiraWorklogApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class WorklogDebtsServiceImplTest {
    @Mock
    private JiraWorklogApiClient jiraWorklogApiClient;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private AuthorsFetchStrategy authorsFetchStrategy;

    @InjectMocks
    private WorklogDebtsServiceImpl worklogDebtsService;

    private static final List<Author> authors = List.of(
            new Author().setName("Lol").setKey("lol_key"),
            new Author().setName("Kek").setKey("kek_key"),
            new Author().setName("Who").setKey("who_key")
    );

    private static final List<Worklog> worklogs = List.of(
            new Worklog()
                    .setId(1L)
                    .setJiraWorklogId(101L)
                    .setAuthor(authors.get(1))
                    .setDateStarted(LocalDateTime.of(2023, 2, 6, 0, 0,0))
                    .setTimeSpentSeconds(3600 * 3L),
            new Worklog()
                    .setId(2L)
                    .setJiraWorklogId(102L)
                    .setAuthor(authors.get(1))
                    .setDateStarted(LocalDateTime.of(2023, 2, 7, 0, 0,0))
                    .setTimeSpentSeconds(3600 * 7L),
            new Worklog()
                    .setId(4L)
                    .setJiraWorklogId(104L)
                    .setAuthor(authors.get(1))
                    .setDateStarted(LocalDateTime.of(2023, 2, 9, 0, 0,0))
                    .setTimeSpentSeconds(3600 * 8L),
            new Worklog()
                    .setId(6L)
                    .setJiraWorklogId(106L)
                    .setAuthor(authors.get(1))
                    .setDateStarted(LocalDateTime.of(2023, 2, 10, 0, 0,0))
                    .setTimeSpentSeconds(3600 * 8L),
            new Worklog()
                    .setId(5L)
                    .setJiraWorklogId(105L)
                    .setAuthor(authors.get(0))
                    .setDateStarted(LocalDateTime.of(2023, 2, 6, 0, 0,0))
                    .setTimeSpentSeconds(3600 * 7L),
            new Worklog()
                    .setId(3L)
                    .setJiraWorklogId(103L)
                    .setAuthor(authors.get(0))
                    .setDateStarted(LocalDateTime.of(2023, 2, 8, 0, 0,0))
                    .setTimeSpentSeconds(3600 * 8L)
    );

    private static final List<EmployeeDto> employees = List.of(
            new EmployeeDto().setJiraKey("lol_key").setFirstName("Lolov"),
            new EmployeeDto().setJiraKey("kek_key").setFirstName("Kekov"),
            new EmployeeDto().setJiraKey("who_key").setFirstName("Whoov")
    );

    private static final long requiredDaySpentSeconds = 3600 * 8L;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void getAllForPeriod_shouldReturnExpectedDebts_whenInputIsDates() {
        when(authorsFetchStrategy.getAuthors()).thenReturn(authors);

        when(employeeService.getEmployeeByJiraKey("lol_key")).thenReturn(Optional.ofNullable(employees.get(0)));
        when(employeeService.getEmployeeByJiraKey("kek_key")).thenReturn(Optional.ofNullable(employees.get(1)));
        when(employeeService.getEmployeeByJiraKey("who_key")).thenReturn(Optional.ofNullable(employees.get(2)));

        LocalDate dateFrom = LocalDate.of(2023, 2, 6);
        LocalDate dateTo = LocalDate.of(2023, 2, 10);

        when(jiraWorklogApiClient.getAllForPeriod(dateFrom, dateTo)).thenReturn(worklogs);

        WorklogDebts actual = worklogDebtsService.getAllForPeriod(dateFrom, dateTo);

        List<DayWorklogDebt> employee0Debts = List.of(
                new DayWorklogDebt()
                        .setDate(LocalDate.of(2023, 2, 6))
                        .setTimeDeptSeconds(requiredDaySpentSeconds - 3600 * 7L),
                new DayWorklogDebt()
                        .setDate(LocalDate.of(2023, 2, 7))
                        .setTimeDeptSeconds(requiredDaySpentSeconds),
                new DayWorklogDebt()
                        .setDate(LocalDate.of(2023, 2, 9))
                        .setTimeDeptSeconds(requiredDaySpentSeconds),
                new DayWorklogDebt()
                        .setDate(LocalDate.of(2023, 2, 10))
                        .setTimeDeptSeconds(requiredDaySpentSeconds));

        List<DayWorklogDebt> employee1Debts = List.of(
                new DayWorklogDebt()
                        .setDate(LocalDate.of(2023, 2, 6))
                        .setTimeDeptSeconds(requiredDaySpentSeconds - 3600 * 3L),
                new DayWorklogDebt()
                        .setDate(LocalDate.of(2023, 2, 7))
                        .setTimeDeptSeconds(requiredDaySpentSeconds - 3600 * 7L),
                new DayWorklogDebt()
                        .setDate(LocalDate.of(2023, 2, 8))
                        .setTimeDeptSeconds(requiredDaySpentSeconds));

        List<DayWorklogDebt> employee2Debts = List.of(
                new DayWorklogDebt()
                        .setDate(LocalDate.of(2023, 2, 6))
                        .setTimeDeptSeconds(requiredDaySpentSeconds),
                new DayWorklogDebt()
                        .setDate(LocalDate.of(2023, 2, 7))
                        .setTimeDeptSeconds(requiredDaySpentSeconds),
                new DayWorklogDebt()
                        .setDate(LocalDate.of(2023, 2, 8))
                        .setTimeDeptSeconds(requiredDaySpentSeconds),
                new DayWorklogDebt()
                        .setDate(LocalDate.of(2023, 2, 9))
                        .setTimeDeptSeconds(requiredDaySpentSeconds),
                new DayWorklogDebt()
                        .setDate(LocalDate.of(2023, 2, 10))
                        .setTimeDeptSeconds(requiredDaySpentSeconds));

        assertAll(
                "Grouped Assertions of WorklogDebts",
                () -> assertEquals(actual.size(), 3),
                () -> assertEquals(actual.get(employees.get(0)), employee0Debts),
                () -> assertEquals(actual.get(employees.get(1)), employee1Debts),
                () -> assertEquals(actual.get(employees.get(2)), employee2Debts)
        );
    }
}
