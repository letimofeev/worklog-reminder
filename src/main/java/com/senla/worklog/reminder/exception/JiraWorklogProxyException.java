package com.senla.worklog.reminder.exception;

public class JiraWorklogProxyException extends RuntimeException {
    public JiraWorklogProxyException(String message) {
        super(message);
    }

    public JiraWorklogProxyException(String message, Throwable cause) {
        super(message, cause);
    }

    public JiraWorklogProxyException(Throwable cause) {
        super(cause);
    }
}
