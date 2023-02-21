package com.senla.worklog.reminder.api.client;

import com.senla.worklog.reminder.model.v3.WorklogV3;

import java.time.LocalDate;
import java.util.List;

public interface JiraWorklogApiClient {
    List<WorklogV3> getAllForPreviousWeek();

    List<WorklogV3> getAllForCurrentWeek();

    List<WorklogV3> getAllForPeriod(LocalDate dateFrom, LocalDate dateTo);
}
