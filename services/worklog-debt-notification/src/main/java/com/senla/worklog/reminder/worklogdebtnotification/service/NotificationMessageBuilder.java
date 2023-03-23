package com.senla.worklog.reminder.worklogdebtnotification.service;

import com.senla.worklog.reminder.worklogdebtnotification.model.EmployeeWorklogDebts;

public interface NotificationMessageBuilder {
    String buildMessage(EmployeeWorklogDebts employeeDebts);
}
