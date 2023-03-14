package com.senla.worklog.reminder.api.notification.client;

import com.senla.worklog.reminder.api.notification.model.Notification;
import com.senla.worklog.reminder.api.notification.model.NotificationResponse;
import com.senla.worklog.reminder.api.notification.model.NotificationUser;
import com.senla.worklog.reminder.config.NotificationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Component
@RequiredArgsConstructor
public class NotificationClientImpl implements NotificationClient {
    private final RestTemplate restTemplate;
    private final NotificationProperties notificationProperties;
    private final WebClient.Builder builder;
    private WebClient client;

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

    @Override
    public List<NotificationUser> getAllUsersByLogins(List<String> logins) {
        var loginParams = String.join(",", logins);
        var uri = notificationProperties.getHost() + "/api/users?login=" + loginParams;
        ResponseEntity<List<NotificationUser>> response = restTemplate.exchange(uri, GET, null,
                new ParameterizedTypeReference<>() {
                });
        return response.getBody();
    }

    @PostConstruct
    public void initClient() {
        client = builder
                .baseUrl(notificationProperties.getHost())
                .build();
    }
}
