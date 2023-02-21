package com.senla.worklog.reminder.api.v3.client;

import com.senla.worklog.reminder.api.v3.model.WorklogV3;

import java.time.LocalDate;
import java.util.List;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.LocalDate.now;

public interface JiraWorklogClientV3 {
    String path = "/rest/tempo-timesheets/3/worklogs";

    default List<WorklogV3> getAllForCurrentWeek() {
        return getAllForPeriod(now().with(MONDAY), now().with(FRIDAY));
    }

    default List<WorklogV3> getAllForPreviousWeek() {
        var previousMonday = now().with(MONDAY).minusWeeks(1);
        var previousFriday = now().with(FRIDAY).minusWeeks(1);
        return getAllForPeriod(previousMonday, previousFriday);
    }

    List<WorklogV3> getAllForPeriod(LocalDate dateFrom, LocalDate dateTo);
}
