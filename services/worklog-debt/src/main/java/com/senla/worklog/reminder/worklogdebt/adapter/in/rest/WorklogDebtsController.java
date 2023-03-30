package com.senla.worklog.reminder.worklogdebt.adapter.in.rest;

import com.senla.worklog.reminder.annotation.DrivingAdapter;
import com.senla.worklog.reminder.worklogdebt.adapter.in.rest.dto.EmployeeWorklogDebtsDto;
import com.senla.worklog.reminder.worklogdebt.adapter.in.rest.mapper.EmployeeWorklogDebtsRestMapper;
import com.senla.worklog.reminder.worklogdebt.adapter.in.rest.validator.DateRangeRequestParameters;
import com.senla.worklog.reminder.worklogdebt.domain.port.in.WorklogDebtsServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.LocalDate.now;
import static java.util.stream.Collectors.toList;

@Validated
@DrivingAdapter
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class WorklogDebtsController implements WorklogDebtsRestAdapter {
    private final WorklogDebtsServicePort debtsServicePort;
    private final EmployeeWorklogDebtsRestMapper debtsRestMapper;

    public List<EmployeeWorklogDebtsDto> getCurrentWeekEmployeesDebts() {
        var dateFrom = now().with(MONDAY);
        var dateTo = now().with(FRIDAY);
        return debtsServicePort.getAllEmployeesDebts(dateFrom, dateTo).stream()
                .map(debtsRestMapper::mapToDto)
                .collect(toList());
    }

    public List<EmployeeWorklogDebtsDto> getEmployeesDebts(LocalDate dateFrom) {
        var dateTo = now().with(FRIDAY);
        return debtsServicePort.getAllEmployeesDebts(dateFrom, dateTo).stream()
                .map(debtsRestMapper::mapToDto)
                .collect(toList());
    }

    public List<EmployeeWorklogDebtsDto> getEmployeesDebts(DateRangeRequestParameters parameters) {
        var dateFrom = parameters.getDateFrom();
        var dateTo = parameters.getDateTo();
        return debtsServicePort.getAllEmployeesDebts(dateFrom, dateTo).stream()
                .map(debtsRestMapper::mapToDto)
                .collect(toList());
    }
}
