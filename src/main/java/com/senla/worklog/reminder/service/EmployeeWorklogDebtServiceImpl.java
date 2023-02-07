package com.senla.worklog.reminder.service;

import com.senla.worklog.reminder.dto.AuthorDto;
import com.senla.worklog.reminder.dto.EmployeeDto;
import com.senla.worklog.reminder.dto.WorklogDto;
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

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
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
    public List<EmployeeWorklogDebt> getAllDebts() {
        List<WorklogDto> previousWeek = jiraWorklogProxy.findAllForPreviousWeek();
        List<AuthorDto> previousWeekAuthors = getAllAuthors(previousWeek);

        List<WorklogDto> currentWeek = jiraWorklogProxy.findAllForCurrentWeek();

        Map<AuthorDto, List<DayWorklogDebt>> debtsByAuthor = getDebtsByAuthor(previousWeekAuthors, currentWeek);
        return mapToEmployeeDebts(debtsByAuthor);
    }

    private List<EmployeeWorklogDebt> mapToEmployeeDebts(Map<AuthorDto, List<DayWorklogDebt>> debtsByAuthor) {
        List<EmployeeWorklogDebt> worklogDebts = new ArrayList<>();
        for (Map.Entry<AuthorDto, List<DayWorklogDebt>> entry : debtsByAuthor.entrySet()) {
            AuthorDto author = entry.getKey();
            List<DayWorklogDebt> authorDebts = entry.getValue();
            EmployeeDto employee = employeeService.getEmployeeByJiraKey(author.getKey());
            worklogDebts.add(new EmployeeWorklogDebt(employee, authorDebts));
        }
        return worklogDebts;
    }

    private Map<AuthorDto, List<DayWorklogDebt>> getDebtsByAuthor(List<AuthorDto> previousWeekAuthors,
                                                                  List<WorklogDto> currentWeek) {
        Map<AuthorDto, List<DayWorklogDebt>> debtsByAuthor = new HashMap<>();
        LocalDate dateFrom = LocalDate.now().with(MONDAY);
        LocalDate dateTo = LocalDate.now().with(SATURDAY);
        for (LocalDate date = dateFrom; date.isBefore(dateTo); date = date.plusDays(1)) {
            List<WorklogDto> dayWorklogs = getDayWorklogs(currentWeek, date);
            Map<AuthorDto, Long> spentTimeByAuthor = getSpentTimeByAuthor(dayWorklogs, previousWeekAuthors);
            addDayDebts(spentTimeByAuthor, date, debtsByAuthor);
        }
        return debtsByAuthor;
    }

    private List<WorklogDto> getDayWorklogs(List<WorklogDto> currentWeek, LocalDate day) {
        return currentWeek.stream()
                .filter(x -> x.getDateStarted().toLocalDate().equals(day))
                .collect(toList());
    }

    private List<AuthorDto> getAllAuthors(List<WorklogDto> worklogs) {
        return worklogs.stream()
                .map(WorklogDto::getAuthor)
                .collect(Collectors.toList());
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

    private Map<AuthorDto, Long> getSpentTimeByAuthor(List<WorklogDto> worklogs, List<AuthorDto> authors) {
        Map<AuthorDto, Long> totalSpentTime = worklogs.stream().collect(groupingBy(WorklogDto::getAuthor,
                summingLong(WorklogDto::getTimeSpentSeconds)));
        for (AuthorDto author : authors) {
            totalSpentTime.putIfAbsent(author, 0L);
        }
        return totalSpentTime;
    }
}
