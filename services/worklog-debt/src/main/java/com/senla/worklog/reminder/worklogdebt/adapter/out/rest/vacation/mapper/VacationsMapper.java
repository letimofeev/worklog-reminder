package com.senla.worklog.reminder.worklogdebt.adapter.out.rest.vacation.mapper;

import com.senla.worklog.reminder.worklogdebt.adapter.out.rest.vacation.dto.VacationDto;
import com.senla.worklog.reminder.worklogdebt.domain.model.ExcludedDay;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VacationsMapper {
    @Mapping(target = "reason", source = "name")
    ExcludedDay mapToExcludedDay(VacationDto vacationDto);
}
