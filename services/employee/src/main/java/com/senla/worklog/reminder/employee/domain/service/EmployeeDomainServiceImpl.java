package com.senla.worklog.reminder.employee.domain.service;

import com.senla.worklog.reminder.employee.domain.exception.DuplicateAttributeException;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import com.senla.worklog.reminder.employee.domain.port.out.EmployeeJpaPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeDomainServiceImpl implements EmployeeDomainService {
    private final EmployeeJpaPort employeeJpaPort;

    @Override
    public void checkUniqueConstraints(Employee employee) {
        log.trace("Checking employee attributes unique constraints");

        checkJiraKeyUniqueness(employee);
        checkSkypeLoginUniqueness(employee);
    }

    private void checkJiraKeyUniqueness(Employee employee) {
        var jiraKey = employee.getJiraKey();
        var employeeId = employee.getId();

        log.trace("Checking employee with id = '{}' jiraKey = '{}' uniqueness", employeeId, jiraKey);

        var employees = employeeJpaPort.getEmployeesByJiraKey(jiraKey);

        if (employees.size() > 1) {
            throw new IllegalStateException("More than 1 employees found by jiraKey");
        }
        if (isDifferentEmployeeFound(employeeId, employees)) {
            throw new DuplicateAttributeException("jiraKey", jiraKey);
        }
    }

    private void checkSkypeLoginUniqueness(Employee employee) {
        var skypeLogin = employee.getSkypeLogin();
        var employeeId = employee.getId();

        log.trace("Checking employee with id = '{}' skypeLogin = '{}' uniqueness", employeeId, skypeLogin);

        var employees = employeeJpaPort.getEmployeesBySkypeLogin(skypeLogin);

        if (employees.size() > 1) {
            throw new IllegalStateException("More than 1 employees found by skypeLogin");
        }
        if (isDifferentEmployeeFound(employeeId, employees)) {
            throw new DuplicateAttributeException("skypeLogin", skypeLogin);
        }
    }

    private boolean isDifferentEmployeeFound(Long employeeId, List<Employee> employees) {
        if (employees.isEmpty()) {
            return false;
        }
        var foundEmployee = employees.get(0);
        return !foundEmployee.getId().equals(employeeId);
    }
}
