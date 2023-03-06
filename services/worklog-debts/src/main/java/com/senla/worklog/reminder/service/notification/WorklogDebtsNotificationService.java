package com.senla.worklog.reminder.service.notification;

import com.senla.worklog.reminder.api.notification.model.NotificationResponse;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.LocalDate.now;

public interface WorklogDebtsNotificationService {
    Flux<NotificationResponse> sendWorklogDebtNotifications(LocalDate dateFrom, LocalDate dateTo);

    default Flux<NotificationResponse> sendWorklogDebtNotifications(LocalDate dateFrom) {
        return sendWorklogDebtNotifications(dateFrom, now());
    }

    default Flux<NotificationResponse> sendWorklogDebtNotifications() {
        return sendWorklogDebtNotifications(now().with(MONDAY), now().with(FRIDAY));
    }
}
