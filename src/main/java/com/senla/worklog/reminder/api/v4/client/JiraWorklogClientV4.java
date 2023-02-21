package com.senla.worklog.reminder.api.v4.client;

import com.senla.worklog.reminder.api.v4.model.WorklogV4;

import java.time.LocalDate;
import java.util.List;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.LocalDate.now;

public interface JiraWorklogClientV4 {
    String path = "/rest/tempo-timesheets/4/worklogs/search";

    default List<WorklogV4> getAllForCurrentWeek() {
        return getAllForPeriod(now().with(MONDAY), now().with(FRIDAY));
    }

    default List<WorklogV4> getAllForPreviousWeek() {
        var previousMonday = now().with(MONDAY).minusWeeks(1);
        var previousFriday = now().with(FRIDAY).minusWeeks(1);
        return getAllForPeriod(previousMonday, previousFriday);
    }

    List<WorklogV4> getAllForPeriod(LocalDate dateFrom, LocalDate dateTo);
}
