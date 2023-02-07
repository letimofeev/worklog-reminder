package com.senla.worklog.reminder.service;

import com.senla.worklog.reminder.model.WorklogDebts;

import java.time.LocalDate;

public interface WorklogDebtsService {
    WorklogDebts getAllForCurrentWeek();

    WorklogDebts getAllForPeriod(LocalDate dateFrom, LocalDate dateTo);
}
