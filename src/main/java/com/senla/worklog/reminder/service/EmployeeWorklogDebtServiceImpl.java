package com.senla.worklog.reminder.service;

import com.senla.worklog.reminder.model.Author;
import com.senla.worklog.reminder.model.Worklog;
import com.senla.worklog.reminder.model.DayWorklogDebt;
import com.senla.worklog.reminder.model.EmployeeWorklogDebt;
import com.senla.worklog.reminder.proxy.JiraWorklogProxy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.DayOfWeek.*;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingLong;
import static java.util.stream.Collectors.toList;

@Service
public class EmployeeWorklogDebtServiceImpl implements EmployeeWorklogDebtService {
    private final JiraWorklogProxy jiraWorklogProxy;
    private final EmployeeService employeeService;

    public EmployeeWorklogDebtServiceImpl(JiraWorklogProxy jiraWorklogProxy, EmployeeService employeeService) {
        this.jiraWorklogProxy = jiraWorklogProxy;
        this.employeeService = employeeService;
    }

    @Override
    public List<EmployeeWorklogDebt> findAllForCurrentWeek() {
        LocalDate dateFrom = LocalDate.now().with(MONDAY);
        LocalDate dateTo = LocalDate.now().with(FRIDAY);
        return findAllForPeriod(dateFrom, dateTo);
    }

    @Override
    public List<EmployeeWorklogDebt> findAllForPeriod(LocalDate dateFrom, LocalDate dateTo) {
        LocalDate dateFromPrev = dateFrom.minusWeeks(1);
        LocalDate dateToPrev = dateTo.minusWeeks(1);
        List<Worklog> previousWeek = jiraWorklogProxy.findAllForPeriod(dateFromPrev, dateToPrev);
        List<Author> previousWeekAuthors = getAllAuthors(previousWeek);

        List<Worklog> worklogs = jiraWorklogProxy.findAllForPeriod(dateFrom, dateTo);

        Map<Author, List<DayWorklogDebt>> debtsByAuthor = getDebtsByAuthor(previousWeekAuthors,
                worklogs, dateFrom, dateTo);
        return mapToEmployeeDebts(debtsByAuthor);
    }

    private List<EmployeeWorklogDebt> mapToEmployeeDebts(Map<Author, List<DayWorklogDebt>> debtsByAuthor) {
        List<EmployeeWorklogDebt> worklogDebts = new ArrayList<>();
        for (Map.Entry<Author, List<DayWorklogDebt>> entry : debtsByAuthor.entrySet()) {
            Author author = entry.getKey();
            List<DayWorklogDebt> authorDebts = entry.getValue();
            employeeService.getEmployeeByJiraKey(author.getKey())
                    .ifPresent(employee -> worklogDebts.add(new EmployeeWorklogDebt(employee, authorDebts)));

        }
        return worklogDebts;
    }

    private Map<Author, List<DayWorklogDebt>> getDebtsByAuthor(List<Author> previousWeekAuthors,
                                                               List<Worklog> currentWeek,
                                                               LocalDate dateFrom, LocalDate dateTo) {
        Map<Author, List<DayWorklogDebt>> debtsByAuthor = new HashMap<>();
        LocalDate dateToExcluding = dateTo.plusDays(1);
        for (LocalDate date = dateFrom; date.isBefore(dateToExcluding); date = date.plusDays(1)) {
            List<Worklog> dayWorklogs = getDayWorklogs(currentWeek, date);
            Map<Author, Long> spentTimeByAuthor = getSpentTimeByAuthor(dayWorklogs, previousWeekAuthors);
            addDayDebts(spentTimeByAuthor, date, debtsByAuthor);
        }
        return debtsByAuthor;
    }

    private List<Worklog> getDayWorklogs(List<Worklog> currentWeek, LocalDate day) {
        return currentWeek.stream()
                .filter(x -> x.getDateStarted().toLocalDate().equals(day))
                .collect(toList());
    }

    private List<Author> getAllAuthors(List<Worklog> worklogs) {
        return worklogs.stream()
                .map(Worklog::getAuthor)
                .collect(Collectors.toList());
    }

    private void addDayDebts(Map<Author, Long> spentTimeByAuthor, LocalDate day,
                             Map<Author, List<DayWorklogDebt>> debtsByAuthor) {
        Long requiredSeconds = 28800L;
        for (Map.Entry<Author, Long> entry : spentTimeByAuthor.entrySet()) {
            Long secondsSpent = entry.getValue();
            long debt = requiredSeconds - secondsSpent;
            if (debt > 0) {
                Author author = entry.getKey();
                debtsByAuthor.putIfAbsent(author, new ArrayList<>());
                debtsByAuthor.get(author).add(new DayWorklogDebt(day, debt));
            }
        }
    }

    private Map<Author, Long> getSpentTimeByAuthor(List<Worklog> worklogs, List<Author> authors) {
        Map<Author, Long> totalSpentTime = worklogs.stream().collect(groupingBy(Worklog::getAuthor,
                summingLong(Worklog::getTimeSpentSeconds)));
        for (Author author : authors) {
            totalSpentTime.putIfAbsent(author, 0L);
        }
        return totalSpentTime;
    }
}
