package com.senla.worklog.reminder.service.notification;


import com.senla.worklog.reminder.api.notification.model.Notification;
import com.senla.worklog.reminder.dto.EmployeeWorklogDebtsDto;

public interface NotificationBuilder {
    Notification buildNotification(EmployeeWorklogDebtsDto employeeDebts);
}
