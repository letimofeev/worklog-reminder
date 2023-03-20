package com.senla.worklog.reminder.employee.domain.exception;

public class DuplicateAttributeException extends DomainException {
    private final String attributeName;
    private final String attributeValue;

    public DuplicateAttributeException(String attributeName, String attributeValue) {
        super("Employee with " + attributeName + " = '" + attributeValue + "' already exists");
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

    public String attributeName() {
        return attributeName;
    }

    public String attributeValue() {
        return attributeValue;
    }
}
