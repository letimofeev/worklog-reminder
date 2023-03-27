package com.senla.worklog.reminder.employee.application.exception;

import com.senla.common.exception.ApplicationException;

/**
 * This class represents an exception that is thrown when the application uniqueness rules violated
 */
public class UniqueConstraintViolationException extends ApplicationException {
    /**
     * The name of the attribute associated with the unique constraint violation
     */
    private final String attributeName;

    /**
     * The value of the attribute associated with the unique constraint violation
     */
    private final String attributeValue;

    public UniqueConstraintViolationException(String message, Throwable cause, String attributeName, String attributeValue) {
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
