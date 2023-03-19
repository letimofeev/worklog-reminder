package com.senla.worklog.reminder.worklogdebtnotification.client;

import com.senla.worklog.reminder.worklogdebtnotification.model.EmployeeWorklogDebts;

import java.time.LocalDate;
import java.util.List;

public interface WorklogDebtClient {
    List<EmployeeWorklogDebts> getEmployeesDebts(LocalDate dateFrom, LocalDate dateTo);
}
