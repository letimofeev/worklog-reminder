package com.senla.worklog.reminder.exception.handler.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * This class extends {@link ApiSubError} and represents a sub-error that occurs when
 * there is an issue with a specific attribute in a request.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AttributeApiSubError extends ApiSubError {
    private String attributeName;
    private String attributeValue;

    public AttributeApiSubError(String message, String attributeName, String attributeValue) {
        super(message);
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

    public AttributeApiSubError setAttributeName(String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    public AttributeApiSubError setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
        return this;
    }
}
