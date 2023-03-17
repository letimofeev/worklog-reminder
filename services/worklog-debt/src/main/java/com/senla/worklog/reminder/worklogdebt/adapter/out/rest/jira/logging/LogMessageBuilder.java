package com.senla.worklog.reminder.worklogdebt.adapter.out.rest.jira.logging;

public interface LogMessageBuilder {
    String buildRequestLogMessage(HttpRequestLog request);

    String buildRequestLogMessage(String header, HttpRequestLog request);
}
