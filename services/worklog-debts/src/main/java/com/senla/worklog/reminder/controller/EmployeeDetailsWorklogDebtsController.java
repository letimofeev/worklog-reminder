package com.senla.worklog.reminder.controller;

import com.senla.worklog.reminder.dto.EmployeeDetailsWorklogDebtsDto;
import com.senla.worklog.reminder.service.worklogdebt.EmployeeDetailsWorklogDebtsService;
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
@RequestMapping("/worklog-debts/details")
@RequiredArgsConstructor
public class EmployeeDetailsWorklogDebtsController {
    private final EmployeeDetailsWorklogDebtsService worklogDebtsService;

    @GetMapping
    public List<EmployeeDetailsWorklogDebtsDto> getCurrentWeekEmployeesDetailsDebts() {
        return worklogDebtsService.getCurrentWeekEmployeesDetailsDebts();
    }

    @GetMapping(params = {"dateFrom"})
    public List<EmployeeDetailsWorklogDebtsDto> getEmployeesDetailsDebts(@PastOrPresent(message = "{dateFrom.pastOrPresent}")
                                                                         @RequestParam
                                                                         @DateTimeFormat(iso = DATE)
                                                                         LocalDate dateFrom) {
        return worklogDebtsService.getEmployeesDetailsDebts(dateFrom);
    }

    @GetMapping(path = "/details", params = {"dateFrom", "dateTo"})
    public List<EmployeeDetailsWorklogDebtsDto> getEmployeesDetailsDebts(@Validated(ValidationSequence.class) DateRangeRequestParameters parameters) {
        var dateFrom = parameters.getDateFrom();
        var dateTo = parameters.getDateTo();
        return worklogDebtsService.getEmployeesDetailsDebts(dateFrom, dateTo);
    }
}
