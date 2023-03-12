package com.senla.worklog.reminder.service.employee;

import com.senla.worklog.reminder.dto.EmployeeDto;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    EmployeeDto addEmployee(EmployeeDto employeeDto);

    Optional<EmployeeDto> getEmployeeById(Long id);

    Optional<EmployeeDto> getEmployeeByJiraKey(String jiraKey);

    List<EmployeeDto> getAllEmployeeByJiraKeys(List<String> jiraKeys);

    List<EmployeeDto> getAllEmployees();

    EmployeeDto updateEmployee(EmployeeDto employeeDto);

    void deleteEmployeeById(Long id);
}
