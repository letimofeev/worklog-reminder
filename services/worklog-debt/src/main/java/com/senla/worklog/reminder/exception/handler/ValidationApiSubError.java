package com.senla.worklog.reminder.exception.handler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ValidationApiSubError extends ApiSubError {
    private Object rejectedValue;

    public ValidationApiSubError(String message, Object rejectedValue) {
        super(message);
        this.rejectedValue = rejectedValue;
    }

    @Override
    public String toString() {
        return "ValidationApiSubError{" +
                "rejectedValue=" + rejectedValue +
                ", message='" + message + '\'' +
                '}';
    }
}
