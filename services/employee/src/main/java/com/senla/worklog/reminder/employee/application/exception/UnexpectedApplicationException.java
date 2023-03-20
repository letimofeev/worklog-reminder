package com.senla.worklog.reminder.employee.application.exception;

public class UnexpectedApplicationException extends ApplicationException {
    public UnexpectedApplicationException(Throwable cause) {
        super("Unexpected application error", cause);
    }

    public UnexpectedApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
