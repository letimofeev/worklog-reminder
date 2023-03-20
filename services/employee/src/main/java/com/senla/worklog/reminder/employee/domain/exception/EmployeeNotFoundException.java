package com.senla.worklog.reminder.employee.domain.exception;

public class EmployeeNotFoundException extends DomainException {
    private final String attributeName;
    private final String attributeValue;

    public EmployeeNotFoundException(String message, String attributeName, String attributeValue) {
        super(message);
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

    public EmployeeNotFoundException(Long employeeId) {
        this("Employee with id = '" + employeeId + "' not found", "id", String.valueOf(employeeId));
    }

    public String getAttributeName() {
        return attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }
}
