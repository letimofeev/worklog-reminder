package com.senla.worklog.reminder.api.notification.client;

import com.senla.worklog.reminder.api.notification.model.Notification;
import com.senla.worklog.reminder.api.notification.model.NotificationResponse;
import com.senla.worklog.reminder.api.notification.model.User;
import reactor.core.publisher.Flux;

import java.util.List;

public interface NotificationClient {
    Flux<NotificationResponse> sendNotifications(List<Notification> notifications);

    List<User> getAllUsersByLogins(List<String> logins);
}
