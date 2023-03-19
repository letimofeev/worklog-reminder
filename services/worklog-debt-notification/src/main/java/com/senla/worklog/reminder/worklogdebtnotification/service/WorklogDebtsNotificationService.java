package com.senla.worklog.reminder.worklogdebtnotification.service;

import com.senla.worklog.reminder.worklogdebtnotification.model.EmployeeWorklogDebts;
import com.senla.worklog.reminder.worklogdebtnotification.model.NotificationResponse;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;

public interface WorklogDebtsNotificationService {
    Flux<NotificationResponse> sendDebtsNotifications(LocalDate dateFrom, LocalDate dateTo);

    Flux<NotificationResponse> sendDebtsNotifications(List<EmployeeWorklogDebts> employeesDebts);
}
