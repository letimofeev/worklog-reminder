package com.senla.worklog.reminder.employee.adapter.in.rest.mapper;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.EmployeeDto;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeRestMapper {
    Employee mapToDomain(EmployeeDto employeeDto);

    EmployeeDto mapToDto(Employee employee);
}
