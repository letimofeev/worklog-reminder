package com.senla.worklog.reminder.service.notification;

import com.senla.worklog.reminder.api.notification.model.Notification;
import com.senla.worklog.reminder.dto.EmployeeWorklogDebtsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationBuilderImpl implements NotificationBuilder {
    private final NotificationMessageBuilder messageBuilder;

    @Override
    public Notification buildNotification(EmployeeWorklogDebtsDto employeeDebts) {
        var login = employeeDebts.getEmployee().getSkypeLogin();
        var message = messageBuilder.buildMessage(employeeDebts);
        return new Notification(login, message);
    }
}
