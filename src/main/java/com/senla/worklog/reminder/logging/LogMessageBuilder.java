package com.senla.worklog.reminder.logging;

public interface LogMessageBuilder {
    String buildRequestLogMessage(LogHttpRequest request);

    String buildRequestLogMessage(String description, LogHttpRequest request);
}
