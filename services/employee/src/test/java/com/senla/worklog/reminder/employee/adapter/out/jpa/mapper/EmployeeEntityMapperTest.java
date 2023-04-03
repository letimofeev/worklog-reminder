package com.senla.worklog.reminder.employee.adapter.out.jpa.mapper;

import com.senla.worklog.reminder.employee.adapter.out.jpa.entity.EmployeeEntity;
import com.senla.worklog.reminder.employee.adapter.out.jpa.entity.RegionEntity;
import com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder.TEST_FIRST_NAME;
import static com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder.TEST_ID;
import static com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder.TEST_JIRA_KEY;
import static com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder.TEST_LAST_NAME;
import static com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder.TEST_SKYPE_LOGIN;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mapstruct.factory.Mappers.getMapper;

class EmployeeEntityMapperTest {
    private final EmployeeEntityMapper entityMapper = getMapper(EmployeeEntityMapper.class);

    @Test
    void mapToJpaEntity_shouldReturnEntity_whenInputIsDomainModel() {
        var domain = new EmployeeTestBuilder().jpaEmployee().build();

        var entity = entityMapper.mapToJpaEntity(domain);

        assertAll("Grouped assertion of employee entity",
                () -> assertEquals(domain.getId(), entity.getId()),
                () -> assertEquals(domain.getFirstName(), entity.getFirstName()),
                () -> assertEquals(domain.getLastName(), entity.getLastName()),
                () -> assertEquals(domain.getJiraKey(), entity.getJiraKey()),
                () -> assertEquals(domain.getSkypeLogin(), entity.getSkypeLogin()),
                () -> assertEquals(domain.getRegion().getId(), entity.getRegion().getId()),
                () -> assertEquals(domain.getRegion().getName(), entity.getRegion().getName()));
    }

    @Test
    void mapToJpaEntity_shouldReturnNull_whenInputIsNull() {
        var entity = entityMapper.mapToJpaEntity(null);

        assertNull(entity);
    }

    @Test
    void mapToDomain_shouldReturnDomain_whenInputIsEntity() {
        var entity = new EmployeeEntity();
        entity.setId(TEST_ID);
        entity.setFirstName(TEST_FIRST_NAME);
        entity.setLastName(TEST_LAST_NAME);
        entity.setJiraKey(TEST_JIRA_KEY);
        entity.setSkypeLogin(TEST_SKYPE_LOGIN);
        entity.setRegion(new RegionEntity(UUID.fromString("ac7bc9b7-1515-4d57-a825-5001a83f8089"), "Name"));

        var domain = entityMapper.mapToDomain(entity);

        assertAll("Grouped assertion of employee entity",
                () -> assertEquals(entity.getId(), domain.getId()),
                () -> assertEquals(entity.getFirstName(), domain.getFirstName()),
                () -> assertEquals(entity.getLastName(), domain.getLastName()),
                () -> assertEquals(entity.getJiraKey(), domain.getJiraKey()),
                () -> assertEquals(entity.getSkypeLogin(), domain.getSkypeLogin()),
                () -> assertEquals(entity.getRegion().getId(), domain.getRegion().getId()),
                () -> assertEquals(entity.getRegion().getName(), domain.getRegion().getName()));
    }

    @Test
    void mapToDomain_shouldReturnNull_whenInputIsNull() {
        var domain = entityMapper.mapToDomain(null);

        assertNull(domain);
    }
}
