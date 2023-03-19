package com.senla.worklog.reminder.worklogdebt.adapter.out.rest.jira.auth;

public class JiraAuthenticationException extends RuntimeException {
    public JiraAuthenticationException(String message) {
        super(message);
    }

    public JiraAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
