package com.senla.worklog.reminder.employee.domain.service;

import com.senla.worklog.reminder.employee.domain.exception.DuplicateAttributeException;
import com.senla.worklog.reminder.employee.domain.model.Employee;

/**
 * This interface defines methods for implementing domain logic and validating employee domain rules
 */
public interface EmployeeDomainService {

    /**
     * Checks the unique constraints of an employee, such as their Jira key and Skype login.
     *
     * @param employee the employee to check
     * @throws DuplicateAttributeException if one or more unique constraints are violated
     */
    void checkUniqueConstraints(Employee employee);
}
