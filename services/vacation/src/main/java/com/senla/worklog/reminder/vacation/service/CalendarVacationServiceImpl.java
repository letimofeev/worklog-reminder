package com.senla.worklog.reminder.vacation.service;

import com.senla.worklog.reminder.vacation.model.CalendarVacation;
import com.senla.worklog.reminder.vacation.model.Region;
import com.senla.worklog.reminder.vacation.repository.CalendarVacationRepository;
import com.senla.worklog.reminder.vacation.repository.RegionRepository;
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
    private final RegionRepository regionRepository;

    @Override
    public boolean isCalendarVacation(LocalDate date) {
        return vacationRepository.existsById(date);
    }

    @Override
    public List<CalendarVacation> getCalendarVacations(UUID regionId, LocalDate dateFrom, LocalDate dateTo) {
        var vacations = vacationRepository.findAllByRegionIdAndDateBetween(regionId, dateFrom, dateTo);
        addWeekends(vacations, dateFrom, dateTo, regionId);
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

    private void addWeekends(List<CalendarVacation> vacations, LocalDate dateFrom, LocalDate dateTo, UUID regionId) {
        var region = getRegion(vacations, regionId);
        var weekends = getWeekends(dateFrom, dateTo, region);
        var calendarVacations = new ArrayList<>(vacations);
        for (var weekend : weekends) {
            var date = weekend.getDate();
            if (!isVacationWeekend(date, calendarVacations)) {
                vacations.add(weekend);
            }
        }
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

    private boolean isVacationWeekend(LocalDate date, List<CalendarVacation> vacations) {
        return vacations.stream()
                .anyMatch(vacation -> vacation.getDate().equals(date));
    }

    private Region getRegion(List<CalendarVacation> vacations, UUID regionId) {
        Region region;
        if (vacations.isEmpty()) {
            region = regionRepository.findById(regionId).orElse(new Region());
        } else {
            region = vacations.get(0).getRegion();
        }
        return region;
    }
}
