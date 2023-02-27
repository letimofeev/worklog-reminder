package com.senla.worklog.reminder.exception.handler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ValidationFailedApiSubError extends ApiSubError {
    private Object rejectedValue;

    public ValidationFailedApiSubError(String message, Object rejectedValue) {
        super(message);
        this.rejectedValue = rejectedValue;
    }

    @Override
    public String toString() {
        return "ValidationFailedApiSubError{" +
                "rejectedValue=" + rejectedValue +
                ", message='" + message + '\'' +
                '}';
    }
}
