package com.senla.worklog.reminder.worklogdebt.domain.port.out;

import com.senla.worklog.reminder.worklogdebt.domain.model.EmployeeWorklogDebts;

import java.time.LocalDate;
import java.util.UUID;

public interface VacationRestPort {
    EmployeeWorklogDebts getVacationDays(UUID regionId, LocalDate dateFrom, LocalDate dateTo, Long employeeId);
}
