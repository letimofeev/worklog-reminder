package com.senla.worklog.reminder.employee.application.exception;

public abstract class ApplicationException extends RuntimeException {
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
