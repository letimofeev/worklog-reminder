package com.senla.worklog.reminder.service.notification;

import com.senla.worklog.reminder.api.notification.model.NotificationResponse;
import com.senla.worklog.reminder.dto.WorklogDebtsDto;
import reactor.core.publisher.Flux;

public interface WorklogDebtsNotificationSender {
    Flux<NotificationResponse> sendWorklogDebtNotifications(WorklogDebtsDto worklogDebtsDto);
}
