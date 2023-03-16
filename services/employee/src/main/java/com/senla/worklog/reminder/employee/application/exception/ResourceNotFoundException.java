package com.senla.worklog.reminder.employee.application.exception;

public class ResourceNotFoundException extends ApplicationException {
    public ResourceNotFoundException(String mainMessage, Throwable cause, String... detailMessages) {
        super(mainMessage, cause, detailMessages);
    }
}
