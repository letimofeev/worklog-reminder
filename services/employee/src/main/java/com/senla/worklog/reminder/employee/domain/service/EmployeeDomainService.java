package com.senla.worklog.reminder.employee.domain.service;

import com.senla.worklog.reminder.employee.domain.model.Employee;

public interface EmployeeDomainService {
    void checkUniqueConstraints(Employee employee);
}
