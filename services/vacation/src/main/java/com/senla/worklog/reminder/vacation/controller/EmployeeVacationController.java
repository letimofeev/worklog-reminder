package com.senla.worklog.reminder.vacation.controller;

import com.senla.worklog.reminder.vacation.model.EmployeeVacation;
import com.senla.worklog.reminder.vacation.service.EmployeeVacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/vacations/employee")
@RequiredArgsConstructor
public class EmployeeVacationController {
    private final EmployeeVacationService vacationService;

    @GetMapping("/{employeeId}")
    public List<EmployeeVacation> getVacationsByEmployeeId(@PathVariable Long employeeId) {
        return vacationService.getEmployeeVacations(employeeId);
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
