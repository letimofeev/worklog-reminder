package com.senla.worklog.reminder.employee.adapter.in.rest.mapper;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.EmployeeDto;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeRestMapper {
    private final ModelMapper mapper;

    public Employee mapToDomain(EmployeeDto employeeDto) {
        return mapper.map(employeeDto, Employee.class);
    }

    public EmployeeDto mapToDto(Employee employee) {
        return mapper.map(employee, EmployeeDto.class);
    }
}
