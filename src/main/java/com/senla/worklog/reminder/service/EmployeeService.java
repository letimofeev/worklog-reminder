package com.senla.worklog.reminder.service;

import com.senla.worklog.reminder.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto addEmployee(EmployeeDto employeeDto);

    EmployeeDto getEmployeeById(Long id);

    EmployeeDto getEmployeeByJiraKey(String jiraKey);

    List<EmployeeDto> getAllEmployees();

    EmployeeDto updateEmployee(EmployeeDto employeeDto);

    void deleteEmployeeById(Long id);
}
