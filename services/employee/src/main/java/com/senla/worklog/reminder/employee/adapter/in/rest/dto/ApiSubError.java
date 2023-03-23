package com.senla.worklog.reminder.employee.adapter.in.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * This class represents a sub-error that provides additional information about an error that occurred
 * when processing an API request.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ApiSubError {
    protected String message;

    @Override
    public String toString() {
        return "ApiSubError{" +
                "message='" + message + '\'' +
                '}';
    }
}
