package com.senla.worklog.reminder.employee.application.service.mapper;

import com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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

    @Test
    void mergeDomains_shouldReturnJpaEmployee_whenRestEmployeeIsNull() {
        var jpaEmployee = new EmployeeTestBuilder().jpaEmployee().build();

        var actual = employeeServiceMapper.mergeDomains(jpaEmployee, null);

        assertEquals(jpaEmployee, actual);
    }

    @Test
    void mergeDomains_shouldReturnRestEmployee_whenJpaEmployeeIsNull() {
        var restEmployee = new EmployeeTestBuilder().restEmployee().build();

        var actual = employeeServiceMapper.mergeDomains(null, restEmployee);

        assertEquals(restEmployee, actual);
    }

    @Test
    void mergeDomains_shouldReturnNull_whenInputIsNulls() {
        var actual = employeeServiceMapper.mergeDomains(null, null);

        assertNull(actual);
    }
}
