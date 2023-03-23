package com.senla.worklog.reminder.worklogdebt.adapter.out.rest.employee.mapper;

import com.senla.worklog.reminder.worklogdebt.adapter.out.rest.employee.dto.EmployeeDto;
import com.senla.worklog.reminder.worklogdebt.domain.model.EmployeeWorklogDebts;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeRestMapper {
    EmployeeWorklogDebts mapToDomain(EmployeeDto employee);
}
