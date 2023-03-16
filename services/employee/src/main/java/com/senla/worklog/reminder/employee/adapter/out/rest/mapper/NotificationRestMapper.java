package com.senla.worklog.reminder.employee.adapter.out.rest.mapper;

import com.senla.worklog.reminder.employee.adapter.out.rest.model.NotificationUser;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationRestMapper {
    @Mapping(target = "skypeLogin", source = "login")
    @Mapping(target = "notificationEnabled", source = "enabled")
    @Mapping(target = "botConnected", expression = "java(user.getId() != null)")
    Employee mapToDomain(NotificationUser user);

    @Mapping(target = "login", source = "employee.skypeLogin")
    @Mapping(target = "enabled", source = "employee.notificationEnabled")
    @Mapping(target = "id", source = "userId")
    NotificationUser mapToNotificationUser(Employee employee, Long userId);
}
