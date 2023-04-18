package com.senla.worklog.reminder.vacation.service;

import com.senla.worklog.reminder.vacation.model.EmployeeVacation;

import java.util.List;

public interface EmployeeVacationService {
    List<EmployeeVacation> getEmployeeVacations(Long employeeId);

    EmployeeVacation addEmployeeVacation(EmployeeVacation employeeVacation);

    EmployeeVacation updateEmployeeVacation(EmployeeVacation employeeVacation);

    void deleteEmployeeVacationById(Long id);
}
