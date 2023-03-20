package com.senla.worklog.reminder.employee.domain.service;

import com.senla.worklog.reminder.employee.domain.exception.DuplicateAttributeException;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import com.senla.worklog.reminder.employee.domain.port.out.EmployeeJpaPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeDomainServiceImpl implements EmployeeDomainService {
    private final EmployeeJpaPort employeeJpaPort;

    @Override
    public void checkUniqueContraints(Employee employee) {
        checkJiraKeyUniqueness(employee.getJiraKey());
        checkSkypeLoginUniqueness(employee.getSkypeLogin());
    }

    private void checkJiraKeyUniqueness(String jiraKey) {
        if (employeeJpaPort.existsByJiraKey(jiraKey)) {
            throw new DuplicateAttributeException("jiraKey", jiraKey);
        }
    }

    private void checkSkypeLoginUniqueness(String skypeLogin) {
        if (employeeJpaPort.existsBySkypeLogin(skypeLogin)) {
            throw new DuplicateAttributeException("skypeLogin", skypeLogin);
        }
    }
}
