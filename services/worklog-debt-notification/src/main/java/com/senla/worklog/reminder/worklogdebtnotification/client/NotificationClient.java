package com.senla.worklog.reminder.worklogdebtnotification.client;

import com.senla.worklog.reminder.worklogdebtnotification.model.Notification;
import com.senla.worklog.reminder.worklogdebtnotification.model.NotificationResponse;
import reactor.core.publisher.Flux;

import java.util.List;

public interface NotificationClient {
    Flux<NotificationResponse> sendNotifications(List<Notification> notifications);
}
