package com.senla.worklog.reminder.vacation.controller;

import com.senla.worklog.reminder.vacation.model.EmployeeVacation;
import com.senla.worklog.reminder.vacation.service.EmployeeVacationService;
import com.senla.worklog.reminder.vacation.dto.DateRangeRequestParameters;
import com.senla.worklog.reminder.vacation.validator.ValidationSequence;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/vacations/employee")
@RequiredArgsConstructor
public class EmployeeVacationController {
    private final EmployeeVacationService vacationService;

    @GetMapping(path = "/{employeeId}", params = {"dateFrom", "dateTo"})
    public List<EmployeeVacation> getVacationsByEmployeeId(@PathVariable Long employeeId,
                                                           @Validated(ValidationSequence.class) DateRangeRequestParameters parameters) {
        var dateFrom = parameters.getDateFrom();
        var dateTo = parameters.getDateTo();
        return vacationService.getEmployeeVacations(employeeId, dateFrom, dateTo);
    }

    @PostMapping
    public EmployeeVacation addEmployeeVacation(@Valid @RequestBody EmployeeVacation employeeVacation) {
        return vacationService.addEmployeeVacation(employeeVacation);
    }

    @PutMapping
    public EmployeeVacation updateEmployeeVacation(@Valid @RequestBody EmployeeVacation employeeVacation) {
        return vacationService.updateEmployeeVacation(employeeVacation);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployeeVacationById(@PathVariable Long id) {
        vacationService.deleteEmployeeVacationById(id);
    }
}
