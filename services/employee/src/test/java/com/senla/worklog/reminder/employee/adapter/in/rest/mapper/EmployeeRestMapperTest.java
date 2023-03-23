package com.senla.worklog.reminder.employee.adapter.in.rest.mapper;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.CreateEmployeeRequestDto;
import com.senla.worklog.reminder.employee.adapter.in.rest.dto.EmployeeDto;
import com.senla.worklog.reminder.employee.adapter.in.rest.dto.UpdateEmployeeRequestDto;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder;
import org.junit.jupiter.api.Test;

import static com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder.TEST_BOT_CONNECTED;
import static com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder.TEST_FIRST_NAME;
import static com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder.TEST_ID;
import static com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder.TEST_JIRA_KEY;
import static com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder.TEST_LAST_NAME;
import static com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder.TEST_NOTIFICATION_ENABLED;
import static com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder.TEST_SKYPE_LOGIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mapstruct.factory.Mappers.getMapper;


class EmployeeRestMapperTest {
    private final EmployeeRestMapper employeeRestMapper = getMapper(EmployeeRestMapper.class);

    @Test
    void mapToDomain_shouldReturnDomain_whenInputIsCreateEmployeeRequestDto() {
        var createRequest = new CreateEmployeeRequestDto()
                .setFirstName("Alex")
                .setLastName("Taylor")
                .setJiraKey("alex-taylor")
                .setSkypeLogin("skype_login");
        var expected = new Employee()
                .setFirstName("Alex")
                .setLastName("Taylor")
                .setJiraKey("alex-taylor")
                .setSkypeLogin("skype_login");

        var actual = employeeRestMapper.mapToDomain(createRequest);

        assertEquals(expected, actual);
    }

    @Test
    void mapToDomain_shouldReturnDomain_whenInputIsUpdateEmployeeRequestDto() {
        var createRequest = new UpdateEmployeeRequestDto()
                .setId(75L)
                .setFirstName("Henry")
                .setLastName("Taylor")
                .setJiraKey("henry")
                .setSkypeLogin("skype_login_henry");
        var expected = new Employee()
                .setId(75L)
                .setFirstName("Henry")
                .setLastName("Taylor")
                .setJiraKey("henry")
                .setSkypeLogin("skype_login_henry");

        var actual = employeeRestMapper.mapToDomain(createRequest);

        assertEquals(expected, actual);
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
                .setNotificationEnabled(TEST_NOTIFICATION_ENABLED)
                .setBotConnected(TEST_BOT_CONNECTED);
        var actual = employeeRestMapper.mapToDto(employee);

        assertEquals(expected, actual);
    }
}