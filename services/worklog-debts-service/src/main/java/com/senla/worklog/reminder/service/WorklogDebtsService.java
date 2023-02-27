package com.senla.worklog.reminder.service;

import com.senla.worklog.reminder.model.WorklogDebts;

import java.time.LocalDate;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.LocalDate.now;

public interface WorklogDebtsService {
    default WorklogDebts getAllForCurrentWeek() {
        return getAllForPeriod(now().with(MONDAY), now().with(FRIDAY));
    }

    WorklogDebts getAllForPeriod(LocalDate dateFrom, LocalDate dateTo);
}
