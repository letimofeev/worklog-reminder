package com.senla.worklog.reminder.dto.mapper;

import com.senla.worklog.reminder.dto.EmployeeDto;
import com.senla.worklog.reminder.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDtoMapperImpl implements EmployeeDtoMapper {
    @Override
    public Employee mapToModel(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setJiraKey(employeeDto.getJiraKey());
        return employee;
    }

    @Override
    public EmployeeDto mapToDto(Employee employee) {
        return new EmployeeDto()
                .setId(employee.getId())
                .setFirstName(employee.getFirstName())
                .setLastName(employee.getLastName())
                .setJiraKey(employee.getJiraKey());
    }
}
