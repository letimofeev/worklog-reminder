package com.senla.worklog.reminder.employee.application.exception;

public class UnmappedApplicationException extends ApplicationException {
    public UnmappedApplicationException(String message, Throwable cause, String... detailMessages) {
        super(message, cause, detailMessages);
    }
}
