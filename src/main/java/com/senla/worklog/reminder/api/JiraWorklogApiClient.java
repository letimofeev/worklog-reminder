package com.senla.worklog.reminder.api;

import com.senla.worklog.reminder.api.v3.model.WorklogV3;

import java.time.LocalDate;
import java.util.List;

public interface JiraWorklogApiClient {
    List<WorklogV3> getAllForPreviousWeek();

    List<WorklogV3> getAllForCurrentWeek();

    List<WorklogV3> getAllForPeriod(LocalDate dateFrom, LocalDate dateTo);
}
