package com.senla.worklog.reminder.service.notification;

import com.senla.worklog.reminder.api.notification.model.NotificationResponse;
import com.senla.worklog.reminder.dto.EmployeeWorklogDebtsDto;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.LocalDate.now;

public interface WorklogDebtsNotificationService {
    Flux<NotificationResponse> sendWorklogDebtNotifications(LocalDate dateFrom, LocalDate dateTo);

    Flux<NotificationResponse> sendWorklogDebtNotifications(List<EmployeeWorklogDebtsDto> worklogDebtsDto);

    default Flux<NotificationResponse> sendWorklogDebtNotifications(LocalDate dateFrom) {
        return sendWorklogDebtNotifications(dateFrom, now());
    }

    default Flux<NotificationResponse> sendWorklogDebtNotifications() {
        return sendWorklogDebtNotifications(now().with(MONDAY), now().with(FRIDAY));
    }
}
