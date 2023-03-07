package com.senla.worklog.reminder.service.worklogdebt;

import com.senla.worklog.reminder.dto.EmployeeDetailsWorklogDebtsDto;

import java.time.LocalDate;
import java.util.List;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.LocalDate.now;

public interface EmployeeDetailsWorklogDebtsService {
    List<EmployeeDetailsWorklogDebtsDto> getEmployeesDetailsDebts(LocalDate dateFrom, LocalDate dateTo);

    default List<EmployeeDetailsWorklogDebtsDto> getEmployeesDetailsDebts(LocalDate dateFrom) {
        return getEmployeesDetailsDebts(dateFrom, now());
    }

    default List<EmployeeDetailsWorklogDebtsDto> getCurrentWeekEmployeesDetailsDebts() {
        return getEmployeesDetailsDebts(now().with(MONDAY), now().with(FRIDAY));
    }
}
