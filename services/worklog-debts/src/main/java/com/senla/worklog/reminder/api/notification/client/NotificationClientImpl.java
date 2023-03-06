package com.senla.worklog.reminder.api.notification.client;

import com.senla.worklog.reminder.api.notification.model.Notification;
import com.senla.worklog.reminder.api.notification.model.NotificationResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
public class NotificationClientImpl implements NotificationClient {
    private final WebClient client = WebClient.create("http://localhost:8200");

    @Override
    public Flux<NotificationResponse> sendNotifications(List<Notification> notifications) {
        var type = new ParameterizedTypeReference<NotificationResponse>() {
        };
        return client.post()
                .uri("/api/notifications")
                .bodyValue(notifications)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(type);
    }
}
