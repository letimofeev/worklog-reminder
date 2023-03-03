package com.senla.worklog.reminder.exception;

public class JiraWorklogApiClientException extends RuntimeException {
    public JiraWorklogApiClientException(String message) {
        super(message);
    }

    public JiraWorklogApiClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public JiraWorklogApiClientException(Throwable cause) {
        super(cause);
    }
}
