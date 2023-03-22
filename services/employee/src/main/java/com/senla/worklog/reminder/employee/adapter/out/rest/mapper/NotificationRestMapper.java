package com.senla.worklog.reminder.employee.adapter.out.rest.mapper;

import com.senla.worklog.reminder.employee.adapter.out.rest.dto.NotificationUserDto;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for mapping between {@link Employee} domain objects and {@link NotificationUserDto} DTOs
 */
@Mapper(componentModel = "spring")
public interface NotificationRestMapper {

    /**
     * Maps a {@link NotificationUserDto} DTO to an {@link Employee} domain object
     *
     * @param user the DTO to map
     * @return the mapped domain object
     */
    @Mapping(target = "skypeLogin", source = "login")
    @Mapping(target = "notificationEnabled", source = "enabled")
    @Mapping(target = "botConnected", expression = "java(user.getId() != null)")
    Employee mapToDomain(NotificationUserDto user);

    /**
     * Maps an {@link Employee} domain object to a {@link NotificationUserDto} DTO
     *
     * @param employee the domain object to map
     * @param userId the ID of the user associated with the DTO
     * @return the mapped DTO
     */
    @Mapping(target = "login", source = "employee.skypeLogin")
    @Mapping(target = "enabled", source = "employee.notificationEnabled")
    @Mapping(target = "id", source = "userId")
    NotificationUserDto mapToNotificationUser(Employee employee, Long userId);
}
