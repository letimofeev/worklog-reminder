package com.senla.worklog.reminder.vacation.service;

import com.senla.worklog.reminder.vacation.model.EmployeeVacation;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeVacationService {
    List<EmployeeVacation> getEmployeeVacations(Long employeeId, LocalDate dateFrom, LocalDate dateTo);

    EmployeeVacation addEmployeeVacation(EmployeeVacation employeeVacation);

    EmployeeVacation updateEmployeeVacation(EmployeeVacation employeeVacation);

    void deleteEmployeeVacationById(Long id);
}
