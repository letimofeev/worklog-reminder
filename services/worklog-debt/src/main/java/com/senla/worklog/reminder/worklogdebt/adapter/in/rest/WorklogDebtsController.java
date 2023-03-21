package com.senla.worklog.reminder.worklogdebt.adapter.in.rest;

import com.senla.worklog.reminder.worklogdebt.adapter.annotation.DrivenAdapter;
import com.senla.worklog.reminder.worklogdebt.adapter.in.rest.dto.EmployeeWorklogDebtsDto;
import com.senla.worklog.reminder.worklogdebt.adapter.in.rest.mapper.EmployeeWorklogDebtsRestMapper;
import com.senla.worklog.reminder.worklogdebt.adapter.in.rest.validator.DateRangeRequestParameters;
import com.senla.worklog.reminder.worklogdebt.adapter.in.rest.validator.ValidationSequence;
import com.senla.worklog.reminder.worklogdebt.domain.port.in.WorklogDebtsServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.LocalDate.now;
import static java.util.stream.Collectors.toList;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;


@Validated
@DrivenAdapter
@CrossOrigin
@RestController
@RequestMapping("/api/worklog-debts")
@RequiredArgsConstructor
public class WorklogDebtsController {
    private final WorklogDebtsServicePort debtsServicePort;
    private final EmployeeWorklogDebtsRestMapper debtsRestMapper;

    @GetMapping
    public List<EmployeeWorklogDebtsDto> getCurrentWeekEmployeesDetailsDebts() {
        var dateFrom = now().with(MONDAY);
        var dateTo = now().with(FRIDAY);
        return debtsServicePort.getAllEmployeesDebts(dateFrom, dateTo).stream()
                .map(debtsRestMapper::mapToDto)
                .collect(toList());
    }

    @GetMapping(params = {"dateFrom"})
    public List<EmployeeWorklogDebtsDto> getEmployeesDetailsDebts(@PastOrPresent(message = "{dateFrom.pastOrPresent}")
                                                                  @RequestParam
                                                                  @DateTimeFormat(iso = DATE)
                                                                  LocalDate dateFrom) {
        var dateTo = now().with(FRIDAY);
        return debtsServicePort.getAllEmployeesDebts(dateFrom, dateTo).stream()
                .map(debtsRestMapper::mapToDto)
                .collect(toList());
    }

    @GetMapping(params = {"dateFrom", "dateTo"})
    public List<EmployeeWorklogDebtsDto> getEmployeesDetailsDebts(@Validated(ValidationSequence.class) DateRangeRequestParameters parameters) {
        var dateFrom = parameters.getDateFrom();
        var dateTo = parameters.getDateTo();
        return debtsServicePort.getAllEmployeesDebts(dateFrom, dateTo).stream()
                .map(debtsRestMapper::mapToDto)
                .collect(toList());
    }
}