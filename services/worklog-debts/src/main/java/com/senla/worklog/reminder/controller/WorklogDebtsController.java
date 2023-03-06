package com.senla.worklog.reminder.controller;

import com.senla.worklog.reminder.dto.EmployeeWorklogDebtsDto;
import com.senla.worklog.reminder.service.worklogdebt.WorklogDebtsService;
import com.senla.worklog.reminder.validator.DateRangeRequestParameters;
import com.senla.worklog.reminder.validator.ValidationSequence;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@Validated
@RestController
@RequestMapping("/worklog-debts")
@RequiredArgsConstructor
public class WorklogDebtsController {
    private final WorklogDebtsService worklogDebtsService;

    @GetMapping
    public List<EmployeeWorklogDebtsDto> getAllDebtsForCurrentWeek() {
        return worklogDebtsService.getAllForCurrentWeek();
    }

    @GetMapping(params = {"dateFrom"})
    public List<EmployeeWorklogDebtsDto> getAllDebtsFrom(@PastOrPresent(message = "{dateFrom.pastOrPresent}")
                                           @RequestParam
                                           @DateTimeFormat(iso = DATE)
                                           LocalDate dateFrom) {
        return worklogDebtsService.getAllFrom(dateFrom);
    }

    @GetMapping(params = {"dateFrom", "dateTo"})
    public List<EmployeeWorklogDebtsDto> getAllDebtsForPeriod(@Validated(ValidationSequence.class) DateRangeRequestParameters parameters) {
        var dateFrom = parameters.getDateFrom();
        var dateTo = parameters.getDateTo();
        return worklogDebtsService.getAllForPeriod(dateFrom, dateTo);
    }
}
