package com.senla.worklog.reminder.service;

import com.senla.worklog.reminder.dto.AuthorDto;
import com.senla.worklog.reminder.dto.EmployeeDto;
import com.senla.worklog.reminder.dto.WorklogDto;
import com.senla.worklog.reminder.model.DayWorklogDebt;
import com.senla.worklog.reminder.model.EmployeeWorklogDebt;
import com.senla.worklog.reminder.proxy.JiraWorklogProxy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.util.stream.Collectors.*;

@Service
public class EmployeeWorklogDebtServiceImpl implements EmployeeWorklogDebtService {
    private final JiraWorklogProxy jiraWorklogProxy;
    private final EmployeeService employeeService;

    public EmployeeWorklogDebtServiceImpl(JiraWorklogProxy jiraWorklogProxy, EmployeeService employeeService) {
        this.jiraWorklogProxy = jiraWorklogProxy;
        this.employeeService = employeeService;
    }

    @Override
    public List<EmployeeWorklogDebt> getAll() {
        List<WorklogDto> previousWeek = jiraWorklogProxy.findAllForPreviousWeek();

        Set<AuthorDto> previousWeekAuthors = previousWeek.stream()
                .map(WorklogDto::getAuthor).collect(Collectors.toSet());

        List<WorklogDto> currentWeek = jiraWorklogProxy.findAllForCurrentWeek();

        Map<AuthorDto, List<DayWorklogDebt>> debtsByAuthor = new HashMap<>();
        LocalDate monday = LocalDate.now().with(MONDAY);
        LocalDate saturday = LocalDate.now().with(SATURDAY);
        for (LocalDate date = monday; date.isBefore(saturday); date = date.plusDays(1)) {
            LocalDate finalDate = date;
            List<WorklogDto> dayWorklogs = currentWeek.stream()
                    .filter(x -> x.getDateStarted().toLocalDate().equals(finalDate))
                    .collect(toList());
            Map<AuthorDto, Long> spentTimeByAuthor = getSpentTimeByAuthor(dayWorklogs, previousWeekAuthors);
            addDayDebts(spentTimeByAuthor, date, debtsByAuthor);
        }
        List<EmployeeWorklogDebt> worklogDebts = new ArrayList<>();
        for (Map.Entry<AuthorDto, List<DayWorklogDebt>> entry : debtsByAuthor.entrySet()) {
            AuthorDto author = entry.getKey();
            List<DayWorklogDebt> authorDebts = entry.getValue();
            EmployeeDto employee = employeeService.getEmployeeByJiraKey(author.getKey());
            worklogDebts.add(new EmployeeWorklogDebt(employee, authorDebts));
        }
        return worklogDebts;
    }

    private void addDayDebts(Map<AuthorDto, Long> spentTimeByAuthor, LocalDate day,
                             Map<AuthorDto, List<DayWorklogDebt>> debtsByAuthor) {
        Long requiredSeconds = 28800L;
        for (Map.Entry<AuthorDto, Long> entry : spentTimeByAuthor.entrySet()) {
            Long secondsSpent = entry.getValue();
            long debt = requiredSeconds - secondsSpent;
            if (debt > 0) {
                AuthorDto author = entry.getKey();
                debtsByAuthor.putIfAbsent(author, new ArrayList<>());
                debtsByAuthor.get(author).add(new DayWorklogDebt(day, debt));
            }
        }
    }

    private Map<AuthorDto, Long> getSpentTimeByAuthor(List<WorklogDto> worklogs,
                                                      Set<AuthorDto> previousWeekAuthors) {
        Map<AuthorDto, Long> totalSpentTime = worklogs.stream().collect(groupingBy(WorklogDto::getAuthor,
                summingLong(WorklogDto::getTimeSpentSeconds)));
        for (AuthorDto author : previousWeekAuthors) {
            totalSpentTime.putIfAbsent(author, 0L);
        }
        return totalSpentTime;
    }
}
