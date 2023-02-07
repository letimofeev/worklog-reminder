package com.senla.worklog.reminder.service;

import com.senla.worklog.reminder.model.EmployeeWorklogDebt;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeWorklogDebtService {
    List<EmployeeWorklogDebt> findAllForCurrentWeek();

    List<EmployeeWorklogDebt> findAllForPeriod(LocalDate dateFrom, LocalDate dateTo);
}
