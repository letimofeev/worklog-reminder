package com.senla.worklog.reminder.employee.application.service.mapper;

import com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mapstruct.factory.Mappers.getMapper;

class EmployeeServiceMapperTest {
    private final EmployeeServiceMapper employeeServiceMapper = getMapper(EmployeeServiceMapper.class);

    @Test
    void mergeDomains_shouldMergeDomainModels_whenInputIsJpaAndRestEmployees() {
        var jpaEmployee = new EmployeeTestBuilder().jpaEmployee().build();
        var restEmployee = new EmployeeTestBuilder().restEmployee().build();

        var expected = new EmployeeTestBuilder().defaultEmployee().build();
        var actual = employeeServiceMapper.mergeDomains(jpaEmployee, restEmployee);

        assertEquals(expected, actual);
    }
}
