package com.senla.worklog.reminder.employee.adapter.out.rest.mapper;

import com.senla.worklog.reminder.employee.adapter.out.rest.dto.NotificationUserDto;
import com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder;
import org.junit.jupiter.api.Test;

import static com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder.TEST_NOTIFICATION_STATUS;
import static com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder.TEST_SKYPE_LOGIN;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mapstruct.factory.Mappers.getMapper;

class NotificationRestMapperTest {
    private final NotificationRestMapper restMapper = getMapper(NotificationRestMapper.class);

    @Test
    void mapToDomain_shouldReturnDomain_whenInputIsNotificationUserDto() {
        var notificationUserDto = new NotificationUserDto()
                .setId(123L)
                .setLogin("login123")
                .setEnabled(true);

        var domain = restMapper.mapToDomain(notificationUserDto);

        assertAll("Grouped assertion of employee domain model",
                () -> assertTrue(domain.getNotificationStatus().isBotConnected()),
                () -> assertTrue(domain.getNotificationStatus().isNotificationEnabled()),
                () -> assertEquals("login123", domain.getSkypeLogin())
        );
    }

    @Test
    void mapToDomain_shouldReturnDomainWithBotDisconnected_whenNotificationUserDtoIdIsNull() {
        var notificationUserDto = new NotificationUserDto().setEnabled(true);

        var domain = restMapper.mapToDomain(notificationUserDto);

        assertAll("Grouped assertion of employee domain model",
                () -> assertFalse(domain.getNotificationStatus().isBotConnected()),
                () -> assertFalse(domain.getNotificationStatus().isNotificationEnabled()),
                () -> assertNull(domain.getSkypeLogin()));
    }

    @Test
    void mapToDomain_shouldReturnNull_whenInputIsNull() {
        var domain = restMapper.mapToDomain(null);

        assertNull(domain);
    }

    @Test
    void mapToNotificationUser_shouldReturnNotificationUserDto_whenInputIsDomainAndId() {
        var domain = new EmployeeTestBuilder().defaultEmployee().build();

        var userDto = restMapper.mapToNotificationUser(domain, 7312L);

        assertAll("Grouped assertion of notification user dto",
                () -> assertEquals(7312L, userDto.getId()),
                () -> assertEquals(TEST_NOTIFICATION_STATUS.isNotificationEnabled(), userDto.isEnabled()),
                () -> assertEquals(TEST_SKYPE_LOGIN, userDto.getLogin()));
    }

    @Test
    void mapToNotificationUser_shouldReturnNotificationUserDtoWithId_whenInputIsNullAndId() {
        var userDto = restMapper.mapToNotificationUser(null, 9L);

        assertAll("Grouped assertion of notification user dto when domain null",
                () -> assertEquals(9L, userDto.getId()),
                () -> assertFalse(userDto.isEnabled()),
                () -> assertNull(userDto.getLogin()));
    }
}
