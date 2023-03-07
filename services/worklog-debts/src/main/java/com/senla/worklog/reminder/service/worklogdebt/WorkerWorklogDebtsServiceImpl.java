package com.senla.worklog.reminder.service.worklogdebt;

import com.senla.worklog.reminder.api.jira.adapter.JiraWorklogClientAdapter;
import com.senla.worklog.reminder.dto.DayWorklogDebtDto;
import com.senla.worklog.reminder.dto.WorkerWorklogDebtsDto;
import com.senla.worklog.reminder.model.Worker;
import com.senla.worklog.reminder.model.Worklog;
import com.senla.worklog.reminder.service.WorkerFetcher;
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
public class WorkerWorklogDebtsServiceImpl implements WorkerWorklogDebtsService {
    private final JiraWorklogClientAdapter worklogClientAdapter;
    private final WorkerFetcher workerFetcher;

    private static final long DEFAULT_REQUIRED_DAY_SECONDS = 3600 * 8L;

    @Override
    public List<WorkerWorklogDebtsDto> getWorkersDebts(LocalDate dateFrom, LocalDate dateTo) {
        var workers = workerFetcher.getWorkers();
        var worklogs = worklogClientAdapter.getAllForPeriod(dateFrom, dateTo);
        return getDebtsByWorker(workers, worklogs, dateFrom, dateTo).entrySet().stream()
                .map(entry -> {
                    var worker = entry.getKey();
                    var worklogDebts = entry.getValue();
                    return new WorkerWorklogDebtsDto(worker, worklogDebts);
                })
                .collect(toList());
    }

    private Map<Worker, List<DayWorklogDebtDto>> getDebtsByWorker(List<Worker> workers, List<Worklog> worklogs,
                                                                  LocalDate dateFrom, LocalDate dateTo) {
        var debtsByWorker = new HashMap<Worker, List<DayWorklogDebtDto>>();
        var dateToExcluding = dateTo.plusDays(1);
        for (var date = dateFrom; date.isBefore(dateToExcluding); date = date.plusDays(1)) {
            if (isWorkingDay(date)) {
                var dayWorklogs = getDayWorklogs(worklogs, date);
                var spentTimeByAuthor = getSpentTimeByWorker(dayWorklogs, workers);
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
                             Map<Worker, List<DayWorklogDebtDto>> debtsByWorker) {
        for (var entry : spentTimeByAuthor.entrySet()) {
            var secondsSpent = entry.getValue();
            long debt = DEFAULT_REQUIRED_DAY_SECONDS - secondsSpent;
            if (debt > 0) {
                var worker = entry.getKey();
                debtsByWorker.putIfAbsent(worker, new ArrayList<>());
                debtsByWorker.get(worker).add(new DayWorklogDebtDto(day, debt));
            }
        }
    }

    private Map<Worker, Long> getSpentTimeByWorker(List<Worklog> worklogs, List<Worker> workers) {
        var totalSpentTime = worklogs.stream()
                .collect(groupingBy(Worklog::getWorker, summingLong(Worklog::getTimeSpentSeconds)));
        for (var worker : workers) {
            totalSpentTime.putIfAbsent(worker, 0L);
        }
        return totalSpentTime;
    }

    private boolean isWorkingDay(LocalDate date) {
        return !(date.get(ChronoField.DAY_OF_WEEK) == 6)
                && !(date.get(ChronoField.DAY_OF_WEEK) == 7);
    }
}
