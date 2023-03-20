package com.senla.worklog.reminder.employee.domain.service;

import com.senla.worklog.reminder.employee.domain.exception.DuplicateAttributeException;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import com.senla.worklog.reminder.employee.domain.port.out.EmployeeJpaPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeDomainServiceImpl implements EmployeeDomainService {
    private final EmployeeJpaPort employeeJpaPort;

    @Override
    public void checkUniqueContraints(Employee employee) {
        log.trace("Checking employee attributes unique constraints");

        checkJiraKeyUniqueness(employee.getJiraKey());
        checkSkypeLoginUniqueness(employee.getSkypeLogin());
    }

    private void checkJiraKeyUniqueness(String jiraKey) {
        log.trace("Checking employee jiraKey = '{}' uniqueness", jiraKey);

        if (employeeJpaPort.existsByJiraKey(jiraKey)) {
            throw new DuplicateAttributeException("jiraKey", jiraKey);
        }
    }

    private void checkSkypeLoginUniqueness(String skypeLogin) {
        log.trace("Checking employee skypeLogin = '{}' uniqueness", skypeLogin);

        if (employeeJpaPort.existsBySkypeLogin(skypeLogin)) {
            throw new DuplicateAttributeException("skypeLogin", skypeLogin);
        }
    }
}
