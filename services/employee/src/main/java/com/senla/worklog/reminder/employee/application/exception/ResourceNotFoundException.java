package com.senla.worklog.reminder.employee.application.exception;

public class ResourceNotFoundException extends ApplicationException {
    private final String attributeName;
    private final String attributeValue;

    public ResourceNotFoundException(String message, Throwable cause, String attributeName, String attributeValue) {
        super(message, cause);
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

    public String causeMessage() {
        return this.getCause().getMessage();
    }

    public String attributeName() {
        return attributeName;
    }

    public String attributeValue() {
        return attributeValue;
    }
}
