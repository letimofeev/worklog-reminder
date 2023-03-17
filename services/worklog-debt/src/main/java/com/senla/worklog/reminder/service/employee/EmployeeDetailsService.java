package com.senla.worklog.reminder.service.employee;

import com.senla.worklog.reminder.dto.EmployeeDetailsDto;
import com.senla.worklog.reminder.dto.EmployeeDto;

import java.util.List;
import java.util.Optional;

public interface EmployeeDetailsService {
    List<EmployeeDetailsDto> getAllEmployeesDetails();

    Optional<EmployeeDetailsDto> getEmployeeDetailsByJiraKey(String jiraKey);

    EmployeeDetailsDto getEmployeeDetailsDto(EmployeeDto employee);
}
