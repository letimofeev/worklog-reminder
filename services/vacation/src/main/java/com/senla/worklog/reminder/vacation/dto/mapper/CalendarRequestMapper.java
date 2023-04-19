package com.senla.worklog.reminder.vacation.dto.mapper;

import com.senla.worklog.reminder.vacation.dto.CalendarVacationRequestDto;
import com.senla.worklog.reminder.vacation.model.CalendarVacation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CalendarRequestMapper {
    @Mapping(target = "region.id", source = "regionId")
    CalendarVacation mapToModel(CalendarVacationRequestDto request);
}
