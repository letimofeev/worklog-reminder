package com.senla.worklog.reminder.controller;

import com.senla.worklog.reminder.dto.WorklogDebtsDto;
import com.senla.worklog.reminder.dto.mapper.WorklogDebtsDtoMapper;
import com.senla.worklog.reminder.model.WorklogDebts;
import com.senla.worklog.reminder.service.WorklogDebtsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Past;
import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@Validated
@RestController
@RequestMapping("/worklog-debts")
public class WorklogDebtsController {
    private final WorklogDebtsService worklogDebtsService;
    private final WorklogDebtsDtoMapper mapper;

    public WorklogDebtsController(WorklogDebtsService worklogDebtsService, WorklogDebtsDtoMapper mapper) {
        this.worklogDebtsService = worklogDebtsService;
        this.mapper = mapper;
    }

    @GetMapping
    public WorklogDebtsDto getAllDebtsForCurrentWeek() {
        WorklogDebts worklogDebts = worklogDebtsService.getAllForCurrentWeek();
        return mapper.mapToDto(worklogDebts);
    }

    @GetMapping(params = {"dateFrom"})
    public WorklogDebtsDto getAllDebtsFrom(@Past(message = "'dateFrom' without specifying 'dateTo' must be past date")
                                           @RequestParam
                                           @DateTimeFormat(iso = DATE)
                                           LocalDate dateFrom) {
        LocalDate dateTo = LocalDate.now();
        WorklogDebts worklogDebts = worklogDebtsService.getAllForPeriod(dateFrom, dateTo);
        return mapper.mapToDto(worklogDebts);
    }

    @GetMapping(params = {"dateFrom", "dateTo"})
    public WorklogDebtsDto getAllDebtsForPeriod(@RequestParam @DateTimeFormat(iso = DATE) LocalDate dateFrom,
                                                @RequestParam @DateTimeFormat(iso = DATE) LocalDate dateTo) {
        WorklogDebts worklogDebts = worklogDebtsService.getAllForPeriod(dateFrom, dateTo);
        return mapper.mapToDto(worklogDebts);
    }
}
