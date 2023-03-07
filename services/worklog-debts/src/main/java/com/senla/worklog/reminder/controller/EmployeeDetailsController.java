package com.senla.worklog.reminder.controller;

import com.senla.worklog.reminder.dto.EmployeeDetailsDto;
import com.senla.worklog.reminder.service.employee.EmployeeDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees/details")
@RequiredArgsConstructor
public class EmployeeDetailsController {
    private final EmployeeDetailsService employeeDetailsService;

    @GetMapping
    public List<EmployeeDetailsDto> getAllEmployeesDetails() {
        return employeeDetailsService.getAllEmployeesDetails();
    }
}
