package com.senla.worklog.reminder.vacation.service;

import com.senla.worklog.reminder.vacation.model.Vacation;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface VacationService {
    List<Vacation> getVacations(UUID regionId, LocalDate dateFrom, LocalDate dateTo, Long employeeId);
}
