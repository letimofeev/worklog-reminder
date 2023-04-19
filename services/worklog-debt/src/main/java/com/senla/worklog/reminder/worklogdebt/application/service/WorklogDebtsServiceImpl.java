package com.senla.worklog.reminder.worklogdebt.application.service;

import com.senla.worklog.reminder.exception.wrapper.annotation.WrappedInApplicationException;
import com.senla.worklog.reminder.worklogdebt.application.service.mapper.WorklogDebtsServiceMapper;
import com.senla.worklog.reminder.worklogdebt.domain.model.DayWorklogDebt;
import com.senla.worklog.reminder.worklogdebt.domain.model.EmployeeWorklogDebts;
import com.senla.worklog.reminder.worklogdebt.domain.model.Worklog;
import com.senla.worklog.reminder.worklogdebt.domain.port.in.WorklogDebtsServicePort;
import com.senla.worklog.reminder.worklogdebt.domain.port.out.EmployeeRestPort;
import com.senla.worklog.reminder.worklogdebt.domain.port.out.JiraRestPort;
import com.senla.worklog.reminder.worklogdebt.domain.port.out.VacationRestPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@WrappedInApplicationException
public class WorklogDebtsServiceImpl implements WorklogDebtsServicePort {
    private final JiraRestPort jiraRestPort;
    private final EmployeeRestPort employeeRestPort;
    private final VacationRestPort vacationRestPort;
    private final WorklogDebtsServiceMapper debtsMapper;

    @Override
    public List<EmployeeWorklogDebts> getAllEmployeesDebts(LocalDate dateFrom, LocalDate dateTo) {
        var worklogs = jiraRestPort.getJiraWorklogs(dateFrom, dateTo);
        var workersKeys = jiraRestPort.getJiraWorkersKeys(dateFrom.minusWeeks(1), dateTo.minusWeeks(1));

        return getDebtsByJiraKey(workersKeys, worklogs, dateFrom, dateTo).entrySet().stream()
                .map(entry -> {
                    var workerJiraKey = entry.getKey();
                    var worklogDebts = entry.getValue();
                    return new EmployeeWorklogDebts()
                            .setJiraKey(workerJiraKey)
                            .setWorklogDebts(worklogDebts);
                })
                .map(jiraDebts -> {
                    var employeeRestDebts = employeeRestPort.getEmployee(jiraDebts);
                    var vacationRestDebts = vacationRestPort.getVacationDays(jiraDebts, dateFrom, dateTo);
                    // TODO: debts.adjustExcludedDays()
                    return debtsMapper.mergeDomains(jiraDebts, employeeRestDebts, vacationRestDebts);
                })
                .collect(toList());
    }

    private Map<String, List<DayWorklogDebt>> getDebtsByJiraKey(List<String> workersKeys, List<Worklog> worklogs,
                                                                LocalDate dateFrom, LocalDate dateTo) {
        var debtsByWorker = new HashMap<String, List<DayWorklogDebt>>();
        var dateToExcluding = dateTo.plusDays(1);
        for (var date = dateFrom; date.isBefore(dateToExcluding); date = date.plusDays(1)) {
            if (isWorkingDay(date)) {
                var dayWorklogs = getDayWorklogs(worklogs, date);
                var spentTimeByAuthor = getSpentTimeByWorkerKey(dayWorklogs, workersKeys);
                addDayDebts(spentTimeByAuthor, date, debtsByWorker);
            }
        }
        return debtsByWorker;
    }

    private List<Worklog> getDayWorklogs(List<Worklog> worklogs, LocalDate day) {
        return worklogs.stream()
                .filter(x -> x.getDateStarted().equals(day))
                .collect(toList());
    }

    private void addDayDebts(Map<String, Long> spentTimeByAuthor, LocalDate day,
                             Map<String, List<DayWorklogDebt>> debtsByWorkerKey) {
        for (var entry : spentTimeByAuthor.entrySet()) {
            var secondsSpent = entry.getValue();
            long debt = 28800 - secondsSpent;
            if (debt > 0) {
                var worker = entry.getKey();
                debtsByWorkerKey.putIfAbsent(worker, new ArrayList<>());
                debtsByWorkerKey.get(worker).add(new DayWorklogDebt(day, debt));
            }
        }
    }

    private Map<String, Long> getSpentTimeByWorkerKey(List<Worklog> worklogs, List<String> workersKeys) {
        var totalSpentTime = worklogs.stream()
                .collect(groupingBy(Worklog::getWorkerJiraKey, summingLong(Worklog::getTimeSpentSeconds)));
        workersKeys.forEach(workerKey -> totalSpentTime.putIfAbsent(workerKey, 0L));
        return totalSpentTime;
    }

    private boolean isWorkingDay(LocalDate date) {
        return !(date.get(ChronoField.DAY_OF_WEEK) == 6)
                && !(date.get(ChronoField.DAY_OF_WEEK) == 7);
    }
}
