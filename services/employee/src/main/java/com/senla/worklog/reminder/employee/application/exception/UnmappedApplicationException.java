package com.senla.worklog.reminder.employee.application.exception;

public class UnmappedApplicationException extends ApplicationException {
    public UnmappedApplicationException(String mainMessage, Throwable cause, String... detailMessages) {
        super(mainMessage, cause, detailMessages);
    }
}
