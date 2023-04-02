package com.senla.worklog.reminder.employee.adapter.in.rabbit.mapper;

import com.senla.worklog.reminder.employee.adapter.in.rabbit.event.data.RegionEventData;
import com.senla.worklog.reminder.employee.domain.model.Region;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegionEventDataMapper {
    Region mapToDomain(RegionEventData eventData);
}
