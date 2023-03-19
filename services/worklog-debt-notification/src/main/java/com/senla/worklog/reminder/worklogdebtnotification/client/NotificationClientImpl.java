package com.senla.worklog.reminder.worklogdebtnotification.client;

import com.senla.worklog.reminder.worklogdebtnotification.config.NotificationProperties;
import com.senla.worklog.reminder.worklogdebtnotification.model.Notification;
import com.senla.worklog.reminder.worklogdebtnotification.model.NotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

@Component
@RequiredArgsConstructor
public class NotificationClientImpl implements NotificationClient {
    private final NotificationProperties notificationProperties;
    private final WebClient client;

    @Override
    public Flux<NotificationResponse> sendNotifications(List<Notification> notifications) {
        var type = new ParameterizedTypeReference<NotificationResponse>() {
        };
        return client.post()
                .uri(notificationProperties.getSendNotificationsUri())
                .bodyValue(notifications)
                .accept(TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(type);
    }
}
