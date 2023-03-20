package com.senla.worklog.reminder.employee.adapter.in.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResourceNotFoundApiSubError extends ApiSubError {
    private String attributeName;
    private String attributeValue;

    public ResourceNotFoundApiSubError setAttributeName(String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    public ResourceNotFoundApiSubError setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
        return this;
    }

    @Override
    public String toString() {
        return "ResourceNotFoundApiSubError{" +
                "attributeName='" + attributeName + '\'' +
                ", attributeValue='" + attributeValue + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
