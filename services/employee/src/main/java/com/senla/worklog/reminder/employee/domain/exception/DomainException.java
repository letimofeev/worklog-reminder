package com.senla.worklog.reminder.employee.domain.exception;

import java.util.Arrays;

public abstract class DomainException extends RuntimeException {
    private String mainMessage;
    private String[] detailMessages;

    public DomainException(String mainMessage, Throwable cause, String... detailMessages) {
        super("Main Message = " + mainMessage + "; Detail Messages = " + Arrays.toString(detailMessages), cause);
        this.mainMessage = mainMessage;
        this.detailMessages = (detailMessages == null) ? (new String[0]) : detailMessages;
    }

    public DomainException(String mainMessage, String... detailMessages) {
        super("Main Message = " + mainMessage + "; Detail Messages = " + Arrays.toString(detailMessages));
        this.mainMessage = mainMessage;
        this.detailMessages = detailMessages;
    }

    public String mainMessage() {
        return mainMessage;
    }

    public boolean hasDetailMessages() {
        return detailMessages.length > 0;
    }

    public String[] detailMessages() {
        return Arrays.copyOf(detailMessages, detailMessages.length);
    }
}
