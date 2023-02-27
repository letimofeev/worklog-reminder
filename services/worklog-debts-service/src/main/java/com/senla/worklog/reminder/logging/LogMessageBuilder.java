package com.senla.worklog.reminder.logging;

public interface LogMessageBuilder {
    String buildRequestLogMessage(LogHttpRequest request);

    String buildRequestLogMessage(String header, LogHttpRequest request);
}
