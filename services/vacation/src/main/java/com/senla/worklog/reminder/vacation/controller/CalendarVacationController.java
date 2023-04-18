package com.senla.worklog.reminder.vacation.controller;

import com.senla.worklog.reminder.vacation.dto.CalendarVacationRequestDto;
import com.senla.worklog.reminder.vacation.dto.mapper.CalendarRequestMapper;
import com.senla.worklog.reminder.vacation.model.CalendarVacation;
import com.senla.worklog.reminder.vacation.service.CalendarVacationService;
import com.senla.worklog.reminder.vacation.dto.DateRangeRequestParameters;
import com.senla.worklog.reminder.vacation.validator.ValidationSequence;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@RestController
@RequestMapping("/api/vacations/calendar")
@RequiredArgsConstructor
public class CalendarVacationController {
    private final CalendarVacationService vacationService;
    private final CalendarRequestMapper calendarRequestMapper;

    @GetMapping(path = "/region/{regionId}", params = {"dateFrom", "dateTo"})
    public List<CalendarVacation> getCalendarVacations(@PathVariable UUID regionId,
                                                       @Validated(ValidationSequence.class) DateRangeRequestParameters parameters) {
        var dateFrom = parameters.getDateFrom();
        var dateTo = parameters.getDateTo();
        return vacationService.getCalendarVacations(regionId, dateFrom, dateTo);
    }

    @PostMapping
    public CalendarVacation addCalendarVacation(@Valid @RequestBody CalendarVacationRequestDto request) {
        var calendarVacation = calendarRequestMapper.mapToModel(request);
        return vacationService.addCalendarVacation(calendarVacation);
    }

    @PutMapping
    public CalendarVacation updateCalendarVacation(@Valid @RequestBody CalendarVacationRequestDto request) {
        var calendarVacation = calendarRequestMapper.mapToModel(request);
        return vacationService.updateCalendarVacation(calendarVacation);
    }

    @DeleteMapping("/{date}")
    public void deleteCalendarVacationById(@PathVariable @DateTimeFormat(iso = DATE) LocalDate date) {
        vacationService.deleteCalendarByDate(date);
    }
}
