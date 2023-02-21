package com.senla.worklog.reminder.service;

import com.senla.worklog.reminder.dto.EmployeeDto;
import com.senla.worklog.reminder.model.v3.Author;
import com.senla.worklog.reminder.model.v3.WorklogV3;
import com.senla.worklog.reminder.model.DayWorklogDebt;
import com.senla.worklog.reminder.model.WorklogDebts;
import com.senla.worklog.reminder.api.client.JiraWorklogApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingLong;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class WorklogDebtsServiceImpl implements WorklogDebtsService {
    private final JiraWorklogApiClient jiraWorklogApiClient;
    private final EmployeeService employeeService;
    private final AuthorsFetchStrategy authorsFetchStrategy;

    private long requiredDaySeconds;

    private static final long DEFAULT_REQUIRED_DAY_SECONDS = 3600 * 8L;

    public WorklogDebtsServiceImpl(JiraWorklogApiClient jiraWorklogApiClient, EmployeeService employeeService,
                                   AuthorsFetchStrategy authorsFetchStrategy) {
        this.jiraWorklogApiClient = jiraWorklogApiClient;
        this.employeeService = employeeService;
        this.authorsFetchStrategy = authorsFetchStrategy;

        this.requiredDaySeconds = DEFAULT_REQUIRED_DAY_SECONDS;
    }

    public void setRequiredDaySeconds(long requiredDaySeconds) {
        this.requiredDaySeconds = requiredDaySeconds;
    }

    @Override
    public WorklogDebts getAllForCurrentWeek() {
        LocalDate dateFrom = LocalDate.now().with(MONDAY);
        LocalDate dateTo = LocalDate.now().with(FRIDAY);
        return getAllForPeriod(dateFrom, dateTo);
    }

    @Override
    public WorklogDebts getAllForPeriod(LocalDate dateFrom, LocalDate dateTo) {
        List<Author> previousWeekAuthors = authorsFetchStrategy.getAuthors();

        List<WorklogV3> worklogs = jiraWorklogApiClient.getAllForPeriod(dateFrom, dateTo);

        Map<Author, List<DayWorklogDebt>> debtsByAuthor = getDebtsByAuthor(previousWeekAuthors,
                worklogs, dateFrom, dateTo);
        return mapToEmployeeDebts(debtsByAuthor);
    }

    private WorklogDebts mapToEmployeeDebts(Map<Author, List<DayWorklogDebt>> debtsByAuthor) {
        WorklogDebts worklogDebts = new WorklogDebts();
        for (Map.Entry<Author, List<DayWorklogDebt>> entry : debtsByAuthor.entrySet()) {
            Author author = entry.getKey();
            List<DayWorklogDebt> authorDebts = entry.getValue();
            employeeService.getEmployeeByJiraKey(author.getKey())
                    .ifPresentOrElse(employee -> worklogDebts.put(employee, authorDebts),
                            () -> handleEmployeeNotFound(worklogDebts, author, authorDebts));
        }
        return worklogDebts;
    }

    private Map<Author, List<DayWorklogDebt>> getDebtsByAuthor(List<Author> authors,
                                                               List<WorklogV3> currentWeek,
                                                               LocalDate dateFrom, LocalDate dateTo) {
        Map<Author, List<DayWorklogDebt>> debtsByAuthor = new HashMap<>();
        LocalDate dateToExcluding = dateTo.plusDays(1);
        for (LocalDate date = dateFrom; date.isBefore(dateToExcluding); date = date.plusDays(1)) {
            if (isWorkingDay(date)) {
                List<WorklogV3> dayWorklogs = getDayWorklogs(currentWeek, date);
                Map<Author, Long> spentTimeByAuthor = getSpentTimeByAuthor(dayWorklogs, authors);
                addDayDebts(spentTimeByAuthor, date, debtsByAuthor);
            }
        }
        return debtsByAuthor;
    }

    private List<WorklogV3> getDayWorklogs(List<WorklogV3> currentWeek, LocalDate day) {
        return currentWeek.stream()
                .filter(x -> x.getDateStarted().toLocalDate().equals(day))
                .collect(toList());
    }

    private void addDayDebts(Map<Author, Long> spentTimeByAuthor, LocalDate day,
                             Map<Author, List<DayWorklogDebt>> debtsByAuthor) {
        for (Map.Entry<Author, Long> entry : spentTimeByAuthor.entrySet()) {
            Long secondsSpent = entry.getValue();
            long debt = requiredDaySeconds - secondsSpent;
            if (debt > 0) {
                Author author = entry.getKey();
                debtsByAuthor.putIfAbsent(author, new ArrayList<>());
                debtsByAuthor.get(author).add(new DayWorklogDebt(day, debt));
            }
        }
    }

    private Map<Author, Long> getSpentTimeByAuthor(List<WorklogV3> worklogs, List<Author> authors) {
        Map<Author, Long> totalSpentTime = worklogs.stream().collect(groupingBy(WorklogV3::getAuthor,
                summingLong(WorklogV3::getTimeSpentSeconds)));
        for (Author author : authors) {
            totalSpentTime.putIfAbsent(author, 0L);
        }
        return totalSpentTime;
    }

    private boolean isWorkingDay(LocalDate date) {
        return !(date.get(ChronoField.DAY_OF_WEEK) == 6)
                && !(date.get(ChronoField.DAY_OF_WEEK) == 7);
    }

    private void handleEmployeeNotFound(WorklogDebts worklogDebts, Author author, List<DayWorklogDebt> authorDebts) {
        EmployeeDto employee = new EmployeeDto()
                .setFirstName(author.getDisplayName())
                .setJiraKey(author.getKey());
        worklogDebts.put(employee, authorDebts);
        log.warn("Employee with jiraKey = '" + author.getKey() + "' not found");
    }
}
