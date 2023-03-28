package com.senla.worklog.reminder.employee.domain.exception;

/**
 * This class represents an exception that is thrown when an attempt is made to create domain model
 * with uniqueness violation attribute
 */
public class DuplicateAttributeException extends RuntimeException {

    /**
     * The name of the attribute associated with the attribute uniqueness violation
     */
    private final String attributeName;

    /**
     * The value of the attribute associated with the attribute uniqueness violation
     */
    private final String attributeValue;

    public DuplicateAttributeException(String message, String attributeName, String attributeValue) {
        super(message);
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

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
