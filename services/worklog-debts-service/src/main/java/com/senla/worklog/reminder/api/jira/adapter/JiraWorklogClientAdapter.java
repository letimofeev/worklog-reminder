package com.senla.worklog.reminder.api.jira.adapter;

import com.senla.worklog.reminder.model.Worklog;

import java.time.LocalDate;
import java.util.List;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.LocalDate.now;

public interface JiraWorklogClientAdapter {
    default List<Worklog> getAllForCurrentWeek() {
        return getAllForPeriod(now().with(MONDAY), now().with(FRIDAY));
    }

    default List<Worklog> getAllForPreviousWeek() {
        LocalDate previousMonday = now().with(MONDAY).minusWeeks(1);
        LocalDate previousFriday = now().with(FRIDAY).minusWeeks(1);
        return getAllForPeriod(previousMonday, previousFriday);
    }

    List<Worklog> getAllForPeriod(LocalDate dateFrom, LocalDate dateTo);
}
