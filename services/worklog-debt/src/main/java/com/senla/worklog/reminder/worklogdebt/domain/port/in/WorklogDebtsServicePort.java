package com.senla.worklog.reminder.worklogdebt.domain.port.in;

import com.senla.worklog.reminder.worklogdebt.domain.model.EmployeeWorklogDebts;

import java.time.LocalDate;
import java.util.List;

public interface WorklogDebtsServicePort {
    List<EmployeeWorklogDebts> getAllEmployeesDebts(LocalDate dateFrom, LocalDate dateTo);
}
