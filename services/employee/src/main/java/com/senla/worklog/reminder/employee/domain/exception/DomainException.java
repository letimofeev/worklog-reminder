package com.senla.worklog.reminder.employee.domain.exception;

import java.util.Arrays;

public abstract class DomainException extends RuntimeException {
    private final String[] detailMessages;

    public DomainException(String message, Throwable cause, String... detailMessages) {
        super(message, cause);
        this.detailMessages = (detailMessages == null) ? (new String[0]) : detailMessages;
    }

    public DomainException(String message, String... detailMessages) {
        super(message);
        this.detailMessages = detailMessages;
    }

    public boolean hasDetailMessages() {
        return detailMessages.length > 0;
    }

    public String[] detailMessages() {
        return Arrays.copyOf(detailMessages, detailMessages.length);
    }
}
