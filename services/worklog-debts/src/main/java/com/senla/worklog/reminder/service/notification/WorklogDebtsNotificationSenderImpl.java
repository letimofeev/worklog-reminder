package com.senla.worklog.reminder.service.notification;

import com.senla.worklog.reminder.api.notification.client.NotificationClient;
import com.senla.worklog.reminder.api.notification.model.NotificationResponse;
import com.senla.worklog.reminder.dto.WorklogDebtsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class WorklogDebtsNotificationSenderImpl implements WorklogDebtsNotificationSender {
    private final NotificationClient notificationClient;
    private final NotificationBuilder notificationBuilder;

    @Override
    public Flux<NotificationResponse> sendWorklogDebtNotifications(WorklogDebtsDto worklogDebtsDto) {
        var notifications = worklogDebtsDto.getWorklogDebts().stream()
                .map(notificationBuilder::buildNotification)
                .collect(toList());
        return notificationClient.sendNotifications(notifications);
    }
}
