package com.senla.common.exception.handler.model;

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
