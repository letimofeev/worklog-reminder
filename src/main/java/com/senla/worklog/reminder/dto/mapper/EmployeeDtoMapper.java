package com.senla.worklog.reminder.dto.mapper;

import com.senla.worklog.reminder.dto.EmployeeDto;
import com.senla.worklog.reminder.model.Employee;

public interface EmployeeDtoMapper {
    Employee mapToModel(EmployeeDto employeeDto);

    EmployeeDto mapToDto(Employee employee);
}
