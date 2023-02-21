package com.senla.worklog.reminder.dto.mapper;

import com.senla.worklog.reminder.dto.EmployeeDto;
import com.senla.worklog.reminder.model.Employee;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeDtoMapperImpl implements EmployeeDtoMapper {
    private final ModelMapper mapper;

    @Override
    public Employee mapToModel(EmployeeDto employeeDto) {
        return mapper.map(employeeDto, Employee.class);
    }

    @Override
    public EmployeeDto mapToDto(Employee employee) {
        return mapper.map(employee, EmployeeDto.class);
    }
}
