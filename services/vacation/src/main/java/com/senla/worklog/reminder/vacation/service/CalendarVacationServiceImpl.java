package com.senla.worklog.reminder.vacation.service;

import com.senla.worklog.reminder.vacation.model.CalendarVacation;
import com.senla.worklog.reminder.vacation.repository.CalendarVacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CalendarVacationServiceImpl implements CalendarVacationService {
    private final CalendarVacationRepository vacationRepository;

    @Override
    public List<CalendarVacation> getCalendarVacations(UUID regionId, LocalDate dateFrom, LocalDate dateTo) {
        return vacationRepository.findAllByRegionIdAndDateBetween(regionId, dateFrom, dateTo);
    }

    @Override
    public CalendarVacation addCalendarVacation(CalendarVacation calendarVacation) {
        return vacationRepository.save(calendarVacation);
    }

    @Override
    public CalendarVacation updateCalendarVacation(CalendarVacation calendarVacation) {
        return vacationRepository.save(calendarVacation);
    }

    @Override
    public void deleteCalendarByDate(LocalDate date) {
        vacationRepository.deleteById(date);
    }
}
