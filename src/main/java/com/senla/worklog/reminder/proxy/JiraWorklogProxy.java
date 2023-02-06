package com.senla.worklog.reminder.proxy;

import com.senla.worklog.reminder.dto.WorklogDto;

import java.time.LocalDate;
import java.util.List;

public interface JiraWorklogProxy {
    List<WorklogDto> findAllForPreviousWeek();

    List<WorklogDto> findAllForCurrentWeek();

    List<WorklogDto> findAllForPeriod(LocalDate dateFrom, LocalDate dateTo);
}
