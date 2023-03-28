package com.senla.worklog.reminder.employee.domain.exception;

/**
 * This class represents an exception that is thrown when a requested employee is not found.
 */
public class EmployeeNotFoundException extends RuntimeException {

    /**
     * The name of the attribute for which the employee was not found
     */
    private final String attributeName;

    /**
     * The value of the attribute for which the employee was not found
     */
    private final String attributeValue;

    public EmployeeNotFoundException(String message, String attributeName, String attributeValue) {
        super(message);
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

    public EmployeeNotFoundException(Long employeeId) {
        this("Employee with id = '" + employeeId + "' not found", "id", String.valueOf(employeeId));
    }

    public String attributeName() {
        return attributeName;
    }

    public String attributeValue() {
        return attributeValue;
    }
}
