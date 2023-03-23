package com.senla.worklog.reminder.worklogdebt.adapter.in.rest.mapper;

import com.senla.worklog.reminder.worklogdebt.adapter.in.rest.dto.EmployeeWorklogDebtsDto;
import com.senla.worklog.reminder.worklogdebt.domain.model.EmployeeWorklogDebts;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeWorklogDebtsRestMapper {
    EmployeeWorklogDebtsDto mapToDto(EmployeeWorklogDebts worklogDebts);
}
