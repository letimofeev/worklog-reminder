package com.senla.worklog.reminder.employee.domain.service;

import com.senla.worklog.reminder.employee.domain.exception.DuplicateAttributeException;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import com.senla.worklog.reminder.employee.domain.port.out.EmployeeJpaPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeDomainServiceImplTest {
    @Mock
    private EmployeeJpaPort employeeJpaPort;

    @InjectMocks
    private EmployeeDomainServiceImpl employeeService;

    @Test
    public void checkUniqueConstraints_shouldThrowException_whenJiraKeyIsNotUnique() {
        var jiraKey = "jira-key-123";
        var skypeLogin = "skype-login-123";
        var employee = new Employee()
                .setJiraKey(jiraKey)
                .setSkypeLogin(skypeLogin);

        when(employeeJpaPort.existsByJiraKey(anyString())).thenReturn(true);

        assertThrows(DuplicateAttributeException.class, () -> employeeService.checkUniqueConstraints(employee));
    }

    @Test
    public void checkUniqueConstraints_shouldThrowException_whenSkypeLoginIsNotUnique() {
        var jiraKey = "jira-key-123";
        var skypeLogin = "skype-login-123";
        var employee = new Employee()
                .setJiraKey(jiraKey)
                .setSkypeLogin(skypeLogin);

        when(employeeJpaPort.existsBySkypeLogin(anyString())).thenReturn(true);

        assertThrows(DuplicateAttributeException.class, () -> employeeService.checkUniqueConstraints(employee));
    }

    @Test
    public void checkUniqueConstraints_shouldNotThrowException_whenJiraKeyAndSkypeLoginAreUnique() {
        var jiraKey = "jira-key-123";
        var skypeLogin = "skype-login-123";
        var employee = new Employee();
        employee.setJiraKey(jiraKey);
        employee.setSkypeLogin(skypeLogin);

        when(employeeJpaPort.existsByJiraKey(anyString())).thenReturn(false);
        when(employeeJpaPort.existsBySkypeLogin(anyString())).thenReturn(false);

        employeeService.checkUniqueConstraints(employee);

        assertDoesNotThrow(() -> employeeService.checkUniqueConstraints(employee));
    }
}
