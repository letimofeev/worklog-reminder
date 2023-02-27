package com.senla.worklog.reminder.service;

import com.senla.worklog.reminder.api.jira.adapter.JiraWorklogClientAdapter;
import com.senla.worklog.reminder.model.Worker;
import com.senla.worklog.reminder.model.Worklog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class PreviousWeekWorkerFetcher implements WorkerFetcher {
    private final JiraWorklogClientAdapter worklogClientAdapter;

    @Override
    public List<Worker> getWorkers() {
        return worklogClientAdapter.getAllForPreviousWeek().stream()
                .map(Worklog::getWorker)
                .distinct()
                .collect(toList());
    }
}
