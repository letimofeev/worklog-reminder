package com.senla.worklog.reminder.exception;

public class JiraAuthenticationException extends RuntimeException {
    public JiraAuthenticationException(String message) {
        super(message);
    }

    public JiraAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JiraAuthenticationException(Throwable cause) {
        super(cause);
    }
}
