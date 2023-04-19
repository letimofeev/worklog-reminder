package com.senla.worklog.reminder.vacation.service;

import com.senla.worklog.reminder.vacation.model.CalendarVacation;
import com.senla.worklog.reminder.vacation.model.Region;
import com.senla.worklog.reminder.vacation.repository.CalendarVacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.util.Comparator.comparing;

@Service
@RequiredArgsConstructor
public class CalendarVacationServiceImpl implements CalendarVacationService {
    private final CalendarVacationRepository vacationRepository;

    @Override
    public boolean isCalendarVacation(LocalDate date) {
        return vacationRepository.existsById(date);
    }

    @Override
    public List<CalendarVacation> getCalendarVacations(UUID regionId, LocalDate dateFrom, LocalDate dateTo) {
        var vacations = vacationRepository.findAllByRegionIdAndDateBetween(regionId, dateFrom, dateTo);
        addWeekends(vacations, dateFrom, dateTo);
        return vacations;
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

    private void addWeekends(List<CalendarVacation> vacations, LocalDate dateFrom, LocalDate dateTo) {
        var region = vacations.get(0).getRegion();
        var weekends = getWeekends(dateFrom, dateTo, region);
        vacations.addAll(weekends);
        vacations.sort(comparing(CalendarVacation::getDate));
    }

    private List<CalendarVacation> getWeekends(LocalDate dateFrom, LocalDate dateTo, Region region) {
        List<CalendarVacation> weekends = new ArrayList<>();
        var date = dateFrom;
        while (!date.isAfter(dateTo)) {
            var dayOfWeek = date.getDayOfWeek();
            if (dayOfWeek == SATURDAY || dayOfWeek == SUNDAY) {
                weekends.add(new CalendarVacation(date, dayOfWeek.name(), region));
            }
            date = date.plusDays(1);
        }
        return weekends;
    }
}
