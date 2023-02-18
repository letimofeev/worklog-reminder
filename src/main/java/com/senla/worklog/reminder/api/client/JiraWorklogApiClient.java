package com.senla.worklog.reminder.api.client;

import com.senla.worklog.reminder.model.Worklog;

import java.time.LocalDate;
import java.util.List;

public interface JiraWorklogApiClient {
    List<Worklog> getAllForPreviousWeek();

    List<Worklog> getAllForCurrentWeek();

    List<Worklog> getAllForPeriod(LocalDate dateFrom, LocalDate dateTo);
}
