package com.senla.worklog.reminder.employee.adapter.in.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AttributeApiSubError extends ApiSubError {
    private String attributeName;
    private String attributeValue;

    public AttributeApiSubError setAttributeName(String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    public AttributeApiSubError setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
        return this;
    }
}
