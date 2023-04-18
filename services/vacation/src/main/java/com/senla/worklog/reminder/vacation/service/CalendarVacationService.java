package com.senla.worklog.reminder.vacation.service;

import com.senla.worklog.reminder.vacation.model.CalendarVacation;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface CalendarVacationService {
    List<CalendarVacation> getCalendarVacations(UUID regionId, LocalDate dateFrom, LocalDate dateTo);

    CalendarVacation addCalendarVacation(CalendarVacation calendarVacation);

    CalendarVacation updateCalendarVacation(CalendarVacation calendarVacation);

    void deleteCalendarByDate(LocalDate date);
}
