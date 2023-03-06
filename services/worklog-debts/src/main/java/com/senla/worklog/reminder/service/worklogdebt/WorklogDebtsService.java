package com.senla.worklog.reminder.service.worklogdebt;

import com.senla.worklog.reminder.model.WorklogDebts;

import java.time.LocalDate;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.LocalDate.now;

public interface WorklogDebtsService {
    WorklogDebts getAllForPeriod(LocalDate dateFrom, LocalDate dateTo);

    default WorklogDebts getAllFrom(LocalDate dateFrom) {
        return getAllForPeriod(dateFrom, now());
    }

    default WorklogDebts getAllForCurrentWeek() {
        return getAllForPeriod(now().with(MONDAY), now().with(FRIDAY));
    }
}
