package com.senla.worklog.reminder.employee.adapter.in.rest.mapper;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.CreateEmployeeRequestDto;
import com.senla.worklog.reminder.employee.adapter.in.rest.dto.EmployeeDto;
import com.senla.worklog.reminder.employee.adapter.in.rest.dto.UpdateEmployeeRequestDto;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeRestMapper {
    Employee mapToDomain(CreateEmployeeRequestDto createEmployeeDto);

    Employee mapToDomain(UpdateEmployeeRequestDto createEmployeeDto);

    EmployeeDto mapToDto(Employee employee);
}
