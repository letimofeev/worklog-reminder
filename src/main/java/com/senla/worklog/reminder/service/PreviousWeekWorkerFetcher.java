package com.senla.worklog.reminder.service;

import com.senla.worklog.reminder.api.adapter.JiraWorklogClientAdapter;
import com.senla.worklog.reminder.model.Worker;
import com.senla.worklog.reminder.model.Worklog;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PreviousWeekWorkerFetcher implements WorkerFetcher {
    private final JiraWorklogClientAdapter worklogClientAdapter;

    public PreviousWeekWorkerFetcher(JiraWorklogClientAdapter worklogClientAdapter) {
        this.worklogClientAdapter = worklogClientAdapter;
    }

    @Override
    public List<Worker> getWorkers() {
        return worklogClientAdapter.getAllForPreviousWeek().stream()
                .map(Worklog::getWorker)
                .distinct()
                .collect(Collectors.toList());
    }
}
