package com.senla.worklog.reminder.controller;

import com.senla.worklog.reminder.dto.WorklogDebtsDto;
import com.senla.worklog.reminder.dto.mapper.WorklogDebtsDtoMapper;
import com.senla.worklog.reminder.service.WorklogDebtsService;
import com.senla.worklog.reminder.validator.GetAllDebtsForPeriodRequestParameters;
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

import static java.time.LocalDate.now;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@Validated
@RestController
@RequestMapping("/worklog-debts")
@RequiredArgsConstructor
public class WorklogDebtsController {
    private final WorklogDebtsService worklogDebtsService;
    private final WorklogDebtsDtoMapper mapper;

    @GetMapping
    public WorklogDebtsDto getAllDebtsForCurrentWeek() {
        var worklogDebts = worklogDebtsService.getAllForCurrentWeek();
        return mapper.mapToDto(worklogDebts);
    }

    @GetMapping(params = {"dateFrom"})
    public WorklogDebtsDto getAllDebtsFrom(@PastOrPresent(message = "{dateFrom.pastOrPresent}")
                                           @RequestParam
                                           @DateTimeFormat(iso = DATE)
                                           LocalDate dateFrom) {
        var dateTo = now();
        var worklogDebts = worklogDebtsService.getAllForPeriod(dateFrom, dateTo);
        return mapper.mapToDto(worklogDebts);
    }

    @GetMapping(params = {"dateFrom", "dateTo"})
    public WorklogDebtsDto getAllDebtsForPeriod(@Validated(ValidationSequence.class) GetAllDebtsForPeriodRequestParameters parameters) {
        var dateFrom = parameters.getDateFrom();
        var dateTo = parameters.getDateTo();
        var worklogDebts = worklogDebtsService.getAllForPeriod(dateFrom, dateTo);
        return mapper.mapToDto(worklogDebts);
    }
}
