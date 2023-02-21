package com.senla.worklog.reminder.service;

import com.senla.worklog.reminder.api.adapter.JiraWorklogClientAdapter;
import com.senla.worklog.reminder.dto.EmployeeDto;
import com.senla.worklog.reminder.model.DayWorklogDebt;
import com.senla.worklog.reminder.model.Worker;
import com.senla.worklog.reminder.model.Worklog;
import com.senla.worklog.reminder.model.WorklogDebts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorklogDebtsServiceImpl implements WorklogDebtsService {
    private final JiraWorklogClientAdapter worklogClientAdapter;
    private final EmployeeService employeeService;
    private final WorkerFetcher workerFetcher;

    private long requiredDaySeconds = DEFAULT_REQUIRED_DAY_SECONDS;

    private static final long DEFAULT_REQUIRED_DAY_SECONDS = 3600 * 8L;

    public void setRequiredDaySeconds(long requiredDaySeconds) {
        this.requiredDaySeconds = requiredDaySeconds;
    }

    @Override
    public WorklogDebts getAllForPeriod(LocalDate dateFrom, LocalDate dateTo) {
        List<Worker> workers = workerFetcher.getWorkers();
        List<Worklog> worklogs = worklogClientAdapter.getAllForPeriod(dateFrom, dateTo);
        Map<Worker, List<DayWorklogDebt>> debtsByAuthor = getDebtsByWorker(workers,
                worklogs, dateFrom, dateTo);
        return mapToEmployeeDebts(debtsByAuthor);
    }

    private WorklogDebts mapToEmployeeDebts(Map<Worker, List<DayWorklogDebt>> debtsByAuthor) {
        WorklogDebts worklogDebts = new WorklogDebts();
        for (Map.Entry<Worker, List<DayWorklogDebt>> entry : debtsByAuthor.entrySet()) {
            Worker worker = entry.getKey();
            List<DayWorklogDebt> authorDebts = entry.getValue();
            employeeService.getEmployeeByJiraKey(worker.getKey())
                    .ifPresentOrElse(employee -> worklogDebts.put(employee, authorDebts),
                            () -> handleEmployeeNotFound(worklogDebts, worker, authorDebts));
        }
        return worklogDebts;
    }

    private Map<Worker, List<DayWorklogDebt>> getDebtsByWorker(List<Worker> workers, List<Worklog> worklogs,
                                                               LocalDate dateFrom, LocalDate dateTo) {
        Map<Worker, List<DayWorklogDebt>> debtsByWorker = new HashMap<>();
        LocalDate dateToExcluding = dateTo.plusDays(1);
        for (LocalDate date = dateFrom; date.isBefore(dateToExcluding); date = date.plusDays(1)) {
            if (isWorkingDay(date)) {
                List<Worklog> dayWorklogs = getDayWorklogs(worklogs, date);
                Map<Worker, Long> spentTimeByAuthor = getSpentTimeByWorker(dayWorklogs, workers);
                addDayDebts(spentTimeByAuthor, date, debtsByWorker);
            }
        }
        return debtsByWorker;
    }

    private List<Worklog> getDayWorklogs(List<Worklog> currentWeek, LocalDate day) {
        return currentWeek.stream()
                .filter(x -> x.getDateStarted().equals(day))
                .collect(toList());
    }

    private void addDayDebts(Map<Worker, Long> spentTimeByAuthor, LocalDate day,
                             Map<Worker, List<DayWorklogDebt>> debtsByWorker) {
        for (Map.Entry<Worker, Long> entry : spentTimeByAuthor.entrySet()) {
            Long secondsSpent = entry.getValue();
            long debt = requiredDaySeconds - secondsSpent;
            if (debt > 0) {
                Worker worker = entry.getKey();
                debtsByWorker.putIfAbsent(worker, new ArrayList<>());
                debtsByWorker.get(worker).add(new DayWorklogDebt(day, debt));
            }
        }
    }

    private Map<Worker, Long> getSpentTimeByWorker(List<Worklog> worklogs, List<Worker> workers) {
        Map<Worker, Long> totalSpentTime = worklogs.stream()
                .collect(groupingBy(Worklog::getWorker,
                        summingLong(Worklog::getTimeSpentSeconds)));
        for (Worker worker : workers) {
            totalSpentTime.putIfAbsent(worker, 0L);
        }
        return totalSpentTime;
    }

    private boolean isWorkingDay(LocalDate date) {
        return !(date.get(ChronoField.DAY_OF_WEEK) == 6)
                && !(date.get(ChronoField.DAY_OF_WEEK) == 7);
    }

    private void handleEmployeeNotFound(WorklogDebts worklogDebts, Worker worker, List<DayWorklogDebt> workerDebts) {
        EmployeeDto employee = new EmployeeDto()
                .setFirstName(worker.getDisplayName())
                .setJiraKey(worker.getKey());
        worklogDebts.put(employee, workerDebts);
        log.warn("Employee with jiraKey = '" + worker.getKey() + "' not found");
    }
}
