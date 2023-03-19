package com.senla.worklog.reminder.worklogdebtnotification.service;

import com.senla.worklog.reminder.worklogdebtnotification.model.EmployeeWorklogDebts;
import com.senla.worklog.reminder.worklogdebtnotification.model.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationBuilderImpl implements NotificationBuilder {
    private final NotificationMessageBuilder messageBuilder;

    @Override
    public Notification buildNotification(EmployeeWorklogDebts employeeDebts) {
        var login = employeeDebts.getSkypeLogin();
        var message = messageBuilder.buildMessage(employeeDebts);
        return new Notification(login, message);
    }
}
