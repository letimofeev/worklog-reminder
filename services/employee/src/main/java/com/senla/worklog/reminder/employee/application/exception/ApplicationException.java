package com.senla.worklog.reminder.employee.application.exception;

import java.util.Arrays;

public abstract class ApplicationException extends RuntimeException {
    private final String[] detailMessages;

    public ApplicationException(String message, Throwable cause, String... detailMessages) {
        super(message, cause);
        this.detailMessages = (detailMessages == null) ? (new String[0]) : detailMessages;
    }
    public boolean hasDetailMessages() {
        return detailMessages.length > 0;
    }

    public String[] detailMessages() {
        return Arrays.copyOf(detailMessages, detailMessages.length);
    }
}
