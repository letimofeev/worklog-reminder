package com.senla.worklog.reminder.service;

import com.senla.worklog.reminder.model.Worker;

import java.util.List;

public interface WorkerFetcher {
    List<Worker> getWorkers();
}
