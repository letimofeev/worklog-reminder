package com.senla.worklog.reminder.worklogdebtnotification.service;

import com.senla.worklog.reminder.worklogdebtnotification.client.NotificationClient;
import com.senla.worklog.reminder.worklogdebtnotification.client.WorklogDebtClient;
import com.senla.worklog.reminder.worklogdebtnotification.model.EmployeeWorklogDebts;
import com.senla.worklog.reminder.worklogdebtnotification.model.NotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class WorklogDebtsNotificationServiceImpl implements WorklogDebtsNotificationService {
    private final WorklogDebtClient worklogDebtClient;
    private final NotificationClient notificationClient;
    private final NotificationBuilder notificationBuilder;

    @Override
    public Flux<NotificationResponse> sendDebtsNotifications(LocalDate dateFrom, LocalDate dateTo) {
        var employeesDebts = worklogDebtClient.getEmployeesDebts(dateFrom, dateTo);
        return sendDebtsNotificationsInternal(employeesDebts);
    }

    @Override
    public Flux<NotificationResponse> sendDebtsNotifications(List<EmployeeWorklogDebts> employeesDebts) {
        return sendDebtsNotificationsInternal(employeesDebts);
    }

    public Flux<NotificationResponse> sendDebtsNotificationsInternal(List<EmployeeWorklogDebts> employeesDebts) {
        var notifications = employeesDebts.stream()
                .map(notificationBuilder::buildNotification)
                .collect(toList());
        return notificationClient.sendNotifications(notifications);
    }
}
