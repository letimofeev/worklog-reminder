package com.senla.worklog.reminder.service.worklogdebt;

import com.senla.worklog.reminder.dto.EmployeeWorklogDebtsDto;

import java.time.LocalDate;
import java.util.List;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.LocalDate.now;

public interface WorklogDebtsService {
    List<EmployeeWorklogDebtsDto> getAllForPeriod(LocalDate dateFrom, LocalDate dateTo);

    default List<EmployeeWorklogDebtsDto> getAllFrom(LocalDate dateFrom) {
        return getAllForPeriod(dateFrom, now());
    }

    default List<EmployeeWorklogDebtsDto> getAllForCurrentWeek() {
        return getAllForPeriod(now().with(MONDAY), now().with(FRIDAY));
    }
}
