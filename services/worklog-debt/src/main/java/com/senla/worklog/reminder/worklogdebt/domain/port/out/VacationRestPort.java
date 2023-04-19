package com.senla.worklog.reminder.worklogdebt.domain.port.out;

import com.senla.worklog.reminder.worklogdebt.domain.model.EmployeeWorklogDebts;

import java.time.LocalDate;

public interface VacationRestPort {
    EmployeeWorklogDebts getVacationDays(EmployeeWorklogDebts worklogDebts, LocalDate dateFrom, LocalDate dateTo);
}
