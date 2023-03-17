package com.senla.worklog.reminder.service.worklogdebt;

import com.senla.worklog.reminder.dto.EmployeeWorklogDebtsDto;

import java.time.LocalDate;
import java.util.List;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.LocalDate.now;

public interface EmployeeWorklogDebtsService {
    List<EmployeeWorklogDebtsDto> getEmployeesDebts(LocalDate dateFrom, LocalDate dateTo);

    default List<EmployeeWorklogDebtsDto> getEmployeesDebts(LocalDate dateFrom) {
        return getEmployeesDebts(dateFrom, now());
    }

    default List<EmployeeWorklogDebtsDto> getCurrentWeekEmployeesDebts() {
        return getEmployeesDebts(now().with(MONDAY), now().with(FRIDAY));
    }
}
