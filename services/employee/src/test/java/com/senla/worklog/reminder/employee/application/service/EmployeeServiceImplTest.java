package com.senla.worklog.reminder.employee.application.service;

import com.senla.worklog.reminder.employee.application.service.mapper.EmployeeServiceMapper;
import com.senla.worklog.reminder.employee.domain.exception.DuplicateAttributeException;
import com.senla.worklog.reminder.employee.domain.exception.EmployeeNotFoundException;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import com.senla.worklog.reminder.employee.domain.model.EmployeeTestBuilder;
import com.senla.worklog.reminder.employee.domain.port.out.EmployeeJpaPort;
import com.senla.worklog.reminder.employee.domain.port.out.NotificationRestPort;
import com.senla.worklog.reminder.employee.domain.service.EmployeeDomainService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    @Mock
    private EmployeeJpaPort employeeJpaPort;

    @Mock
    private NotificationRestPort notificationRestPort;

    @Mock
    private EmployeeServiceMapper mapper;

    @Mock
    private EmployeeDomainService domainService;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    public void addEmployee_shouldAddEmployee_whenUniqueConstraintsAreMet() {
        var employee = new Employee()
                .setJiraKey("TEST-123")
                .setSkypeLogin("test.user");

        when(employeeJpaPort.addEmployee(any(Employee.class))).thenReturn(employee);
        when(mapper.mergeDomains(eq(employee), any())).thenReturn(employee);

        var actual = employeeService.addEmployee(employee);

        assertNotNull(actual);
        assertEquals(employee, actual);

        verify(domainService).checkUniqueConstraints(employee);
    }

    @Test
    public void addEmployee_shouldThrowException_whenJiraKeyIsNotUnique() {
        var employee = new Employee().setJiraKey("TEST-123");

        doThrow(new DuplicateAttributeException("jiraKey", "TEST-123"))
                .when(domainService).checkUniqueConstraints(employee);

        assertThrows(DuplicateAttributeException.class, () -> employeeService.addEmployee(employee));
    }

    @Test
    public void addEmployee_shouldThrowException_whenSkypeLoginIsNotUnique() {
        var employee = new Employee().setSkypeLogin("test.user");

        doThrow(new DuplicateAttributeException("skypeLogin", "test.user"))
                .when(domainService).checkUniqueConstraints(employee);

        assertThrows(DuplicateAttributeException.class, () -> employeeService.addEmployee(employee));
    }

    @Test
    public void getEmployeeById_shouldReturnEmployee_whenEmployeeExists() {
        var jpaEmployee = new EmployeeTestBuilder().jpaEmployee().build();
        var restEmployee = new EmployeeTestBuilder().restEmployee().build();
        var employee = new EmployeeTestBuilder().defaultEmployee().build();

        when(employeeJpaPort.getEmployeeById(1L)).thenReturn(jpaEmployee);
        when(notificationRestPort.getNotificationEmployee(jpaEmployee)).thenReturn(restEmployee);
        when(mapper.mergeDomains(jpaEmployee, restEmployee)).thenReturn(employee);

        var actual = employeeService.getEmployeeById(1L);

        assertNotNull(actual);
        assertEquals(employee, actual);
    }

    @Test
    public void getEmployeeById_shouldThrowException_whenEmployeeDoesNotExist() {
        when(employeeJpaPort.getEmployeeById(1L)).thenThrow(new EmployeeNotFoundException(1L));

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(1L));
    }

    @Test
    public void getEmployeesByJiraKey_shouldReturnListOfEmployee_whenEmployeeExists() {
        var jpaEmployee = new EmployeeTestBuilder().jpaEmployee().build().setJiraKey("key-123");
        var restEmployee = new EmployeeTestBuilder().restEmployee().build();
        var employee = new EmployeeTestBuilder().defaultEmployee().build();

        when(employeeJpaPort.getEmployeesByJiraKey("key-123")).thenReturn(List.of(jpaEmployee));
        when(notificationRestPort.getNotificationEmployee(jpaEmployee)).thenReturn(restEmployee);
        when(mapper.mergeDomains(jpaEmployee, restEmployee)).thenReturn(employee);

        var expected = List.of(employee);
        var actual = employeeService.getEmployeesByJiraKey("key-123");

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void getEmployeesByJiraKey_shouldReturnEmptyList_whenEmployeeDoesNotExist() {
        when(employeeJpaPort.getEmployeesByJiraKey("key-123")).thenReturn(List.of());

        var expected = List.of();
        var actual = employeeService.getEmployeesByJiraKey("key-123");

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void getAllEmployees_shouldReturnEmployees_whenEmployeesExist() {
        var jpaEmployee1 = new EmployeeTestBuilder().jpaEmployee().build().setId(1L);
        var jpaEmployee2 = new EmployeeTestBuilder().jpaEmployee().build().setId(2L);

        when(employeeJpaPort.getAllEmployees()).thenReturn(List.of(jpaEmployee1, jpaEmployee2));

        var restEmployee1 = new EmployeeTestBuilder().restEmployee().build();
        var restEmployee2 = new EmployeeTestBuilder().restEmployee().build();

        when(notificationRestPort.getNotificationEmployee(jpaEmployee1)).thenReturn(restEmployee1);
        when(notificationRestPort.getNotificationEmployee(jpaEmployee2)).thenReturn(restEmployee2);

        var domainEmployee1 = new EmployeeTestBuilder().defaultEmployee().build().setId(1L);
        var domainEmployee2 = new EmployeeTestBuilder().defaultEmployee().build().setId(2L);

        when(mapper.mergeDomains(jpaEmployee1, restEmployee1)).thenReturn(domainEmployee1);
        when(mapper.mergeDomains(jpaEmployee2, restEmployee2)).thenReturn(domainEmployee2);

        var actual = employeeService.getAllEmployees();
        var expected = List.of(domainEmployee1, domainEmployee2);

        assertEquals(expected, actual);
    }

    @Test
    public void updateEmployee_shouldReturnUpdatedEmployee_whenInputIsEmployee() {
        var jpaEmployee = new EmployeeTestBuilder().jpaEmployee().build();
        var restEmployee = new EmployeeTestBuilder().restEmployee().build();
        var employee = new EmployeeTestBuilder().defaultEmployee().build();

        when(employeeJpaPort.updateEmployee(employee)).thenReturn(jpaEmployee);
        when(notificationRestPort.updateEmployee(employee)).thenReturn(restEmployee);
        when(mapper.mergeDomains(jpaEmployee, restEmployee)).thenReturn(employee);

        var actual = employeeService.updateEmployee(employee);

        assertEquals(employee, actual);

        verify(employeeJpaPort).updateEmployee(employee);
        verify(notificationRestPort).updateEmployee(employee);
        verify(domainService).checkUniqueConstraints(employee);
        verify(mapper).mergeDomains(jpaEmployee, restEmployee);
    }

    @Test
    void deleteEmployeeById_shouldDeleteEmployee_whenEmployeeExists() {
        var jpaEmployee = new EmployeeTestBuilder().jpaEmployee().build();

        when(employeeJpaPort.getEmployeeById(10L)).thenReturn(jpaEmployee);

        employeeService.deleteEmployeeById(10L);

        verify(employeeJpaPort).getEmployeeById(10L);
        verify(employeeJpaPort).deleteEmployeeById(10L);
        verify(notificationRestPort).deleteEmployee(jpaEmployee);
    }

    @Test
    void deleteEmployeeById_shouldThrowException_whenEmployeeDoesNotExist() {
        when(employeeJpaPort.getEmployeeById(109L)).thenThrow(new EmployeeNotFoundException(109L));

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(109L));
    }
}
