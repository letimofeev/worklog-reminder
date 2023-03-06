package com.senla.worklog.reminder.service.notification;

import com.senla.worklog.reminder.dto.EmployeeWorklogDebtsDto;

public interface NotificationMessageBuilder {
    String buildMessage(EmployeeWorklogDebtsDto employeeDebts);
}
