package com.senla.worklog.reminder.employee.application.exception;

import java.util.Arrays;

public abstract class ApplicationException extends RuntimeException {
    private String mainMessage;
    private String[] detailMessages;

    public ApplicationException(String mainMessage, Throwable cause, String... detailMessages) {
        super("Main Message = " + mainMessage + "; Detail Messages = " + Arrays.toString(detailMessages), cause);
        this.mainMessage = mainMessage;
        this.detailMessages = (detailMessages == null) ? (new String[0]) : detailMessages;
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
