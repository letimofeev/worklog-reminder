package com.senla.worklog.reminder.proxy;

import com.senla.worklog.reminder.model.Worklog;

import java.time.LocalDate;
import java.util.List;

public interface JiraWorklogProxy {
    List<Worklog> findAllForPreviousWeek();

    List<Worklog> findAllForPeriod(LocalDate dateFrom, LocalDate dateTo);
}
