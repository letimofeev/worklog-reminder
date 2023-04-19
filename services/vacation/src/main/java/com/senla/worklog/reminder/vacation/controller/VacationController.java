package com.senla.worklog.reminder.vacation.controller;

import com.senla.worklog.reminder.vacation.dto.DateRangeRequestParameters;
import com.senla.worklog.reminder.vacation.dto.VacationsResponse;
import com.senla.worklog.reminder.vacation.service.VacationService;
import com.senla.worklog.reminder.vacation.validator.ValidationSequence;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/vacations")
@RequiredArgsConstructor
public class VacationController {
    private final VacationService vacationService;

    @GetMapping(path = "/region/{regionId}", params = {"dateFrom", "dateTo", "employeeId"})
    public VacationsResponse getVacations(@PathVariable UUID regionId,
                                          @Validated(ValidationSequence.class) DateRangeRequestParameters parameters,
                                          Long employeeId) {
        var dateFrom = parameters.getDateFrom();
        var dateTo = parameters.getDateTo();
        var vacations = vacationService.getVacations(regionId, dateFrom, dateTo, employeeId);
        return new VacationsResponse(employeeId, vacations);
    }
}
