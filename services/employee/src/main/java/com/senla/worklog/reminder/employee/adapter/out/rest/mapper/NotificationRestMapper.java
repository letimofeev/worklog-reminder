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
     * @param notificationUserDto the DTO to map
     * @return the mapped domain object
     */
    @Mapping(target = "skypeLogin", source = "login")
    @Mapping(target = "notificationStatus.notificationEnabled", expression = "java(notificationUserDto.isEnabled() && notificationUserDto.getId() != null)")
    @Mapping(target = "notificationStatus.botConnected", expression = "java(notificationUserDto.getId() != null)")
    Employee mapToDomain(NotificationUserDto notificationUserDto);

    /**
     * Maps an {@link Employee} domain object to a {@link NotificationUserDto} DTO
     *
     * @param employee the domain object to map
     * @param userId the ID of the user associated with the DTO
     * @return the mapped DTO
     */
    @Mapping(target = "login", source = "employee.skypeLogin")
    @Mapping(target = "enabled", source = "employee.notificationStatus.notificationEnabled")
    @Mapping(target = "id", source = "userId")
    NotificationUserDto mapToNotificationUser(Employee employee, Long userId);
}
