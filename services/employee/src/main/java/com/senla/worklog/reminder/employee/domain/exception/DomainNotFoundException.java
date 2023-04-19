package com.senla.worklog.reminder.employee.domain.exception;

import com.senla.worklog.reminder.exception.DomainException;

public class DomainNotFoundException extends DomainException {
    /**
     * The name of the attribute for which the employee was not found
     */
    private final String attributeName;

    /**
     * The value of the attribute for which the employee was not found
     */
    private final String attributeValue;

    public DomainNotFoundException(String message, String attributeName, String attributeValue) {
        super(message);
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
