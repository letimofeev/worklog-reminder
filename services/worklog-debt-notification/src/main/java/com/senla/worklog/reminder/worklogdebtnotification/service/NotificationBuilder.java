package com.senla.worklog.reminder.worklogdebtnotification.service;

import com.senla.worklog.reminder.worklogdebtnotification.model.EmployeeWorklogDebts;
import com.senla.worklog.reminder.worklogdebtnotification.model.Notification;

public interface NotificationBuilder {
    Notification buildNotification(EmployeeWorklogDebts employeeDebts);
}
