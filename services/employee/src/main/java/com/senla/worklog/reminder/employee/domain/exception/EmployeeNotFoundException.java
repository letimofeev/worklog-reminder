package com.senla.worklog.reminder.employee.domain.exception;

/**
 * This class represents an exception that is thrown when a requested employee is not found.
 */
public class EmployeeNotFoundException extends DomainNotFoundException {
    public EmployeeNotFoundException(Long employeeId) {
        super("Employee with id = '" + employeeId + "' not found", "id", String.valueOf(employeeId));
    }

    public EmployeeNotFoundException(String message, String attributeName, String attributeValue) {
        super(message, attributeName, attributeValue);
    }
}
