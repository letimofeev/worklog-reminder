package com.senla.worklog.reminder.employee.application.exception;

import com.senla.common.exception.ApplicationException;

/**
 * This class represents an exception that is thrown when a requested resource is not found.
 */
public class ResourceNotFoundException extends ApplicationException {
    /**
     * The name of the attribute for which the resource was not found
     */
    private final String attributeName;

    /**
     * The value of the attribute for which the resource was not found
     */
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
