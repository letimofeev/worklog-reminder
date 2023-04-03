package com.senla.worklog.reminder.employee.adapter.in.rest.mapper;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.CreateEmployeeRequestDto;
import com.senla.worklog.reminder.employee.adapter.in.rest.dto.EmployeeDto;
import com.senla.worklog.reminder.employee.adapter.in.rest.dto.NotificationStatusDto;
import com.senla.worklog.reminder.employee.adapter.in.rest.dto.RegionDto;
import com.senla.worklog.reminder.employee.adapter.in.rest.dto.UpdateEmployeeRequestDto;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder;
import com.senla.worklog.reminder.employee.domain.model.NotificationStatus;
import com.senla.worklog.reminder.employee.domain.model.Region;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder.TEST_FIRST_NAME;
import static com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder.TEST_ID;
import static com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder.TEST_JIRA_KEY;
import static com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder.TEST_LAST_NAME;
import static com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder.TEST_NOTIFICATION_STATUS;
import static com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder.TEST_REGION;
import static com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder.TEST_SKYPE_LOGIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mapstruct.factory.Mappers.getMapper;


class EmployeeRestMapperTest {
    private final EmployeeRestMapper employeeRestMapper = getMapper(EmployeeRestMapper.class);

    @Test
    void mapToDomain_shouldReturnDomain_whenInputIsCreateEmployeeRequestDto() {
        var createRequest = new CreateEmployeeRequestDto()
                .setFirstName("Alex")
                .setLastName("Taylor")
                .setJiraKey("alex-taylor")
                .setSkypeLogin("skype_login")
                .setRegionId(UUID.fromString("ac7bc9b7-1515-4d57-a825-5001a83f0000"));
        var expected = new Employee()
                .setFirstName("Alex")
                .setLastName("Taylor")
                .setJiraKey("alex-taylor")
                .setSkypeLogin("skype_login")
                .setRegion(new Region().setId(UUID.fromString("ac7bc9b7-1515-4d57-a825-5001a83f0000")));

        var actual = employeeRestMapper.mapToDomain(createRequest);

        assertEquals(expected, actual);
    }

    @Test
    void mapToDomain_shouldReturnDomain_whenInputIsUpdateEmployeeRequestDto() {
        var updateRequest = new UpdateEmployeeRequestDto()
                .setId(75L)
                .setFirstName("Henry")
                .setLastName("Taylor")
                .setJiraKey("henry")
                .setSkypeLogin("skype_login_henry")
                .setNotificationEnabled(true)
                .setRegionId(UUID.fromString("ac7bc9b7-1515-4d57-a825-5001a83f0000"));
        var expected = new Employee()
                .setId(75L)
                .setFirstName("Henry")
                .setLastName("Taylor")
                .setJiraKey("henry")
                .setSkypeLogin("skype_login_henry")
                .setNotificationStatus(new NotificationStatus().setNotificationEnabled(true))
                .setRegion(new Region().setId(UUID.fromString("ac7bc9b7-1515-4d57-a825-5001a83f0000")));

        var actual = employeeRestMapper.mapToDomain(updateRequest);

        assertEquals(expected, actual);
    }

    @Test
    void mapToDomain_shouldNull_whenInputIsNull() {
        var create = employeeRestMapper.mapToDomain((CreateEmployeeRequestDto) null);
        var update = employeeRestMapper.mapToDomain((UpdateEmployeeRequestDto) null);

        assertNull(create);
        assertNull(update);
    }

    @Test
    void mapToDto_shouldReturnDto_whenInputIsDomain() {
        var employee = new EmployeeTestBuilder().defaultEmployee().build();

        var expected = new EmployeeDto()
                .setId(TEST_ID)
                .setFirstName(TEST_FIRST_NAME)
                .setLastName(TEST_LAST_NAME)
                .setJiraKey(TEST_JIRA_KEY)
                .setSkypeLogin(TEST_SKYPE_LOGIN)
                .setRegion(new RegionDto(TEST_REGION.getId(), TEST_REGION.getName()))
                .setNotificationStatus(new NotificationStatusDto(TEST_NOTIFICATION_STATUS.isNotificationEnabled(),
                        TEST_NOTIFICATION_STATUS.isBotConnected()));
        var actual = employeeRestMapper.mapToDto(employee);

        assertEquals(expected, actual);
    }

    @Test
    void mapToDto_shouldReturnNull_whenInputIsNull() {
        var dto = employeeRestMapper.mapToDto(null);

        assertNull(dto);
    }
}
