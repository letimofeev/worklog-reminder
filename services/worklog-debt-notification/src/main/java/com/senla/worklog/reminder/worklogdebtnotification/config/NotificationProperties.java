package com.senla.worklog.reminder.worklogdebtnotification.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "notification.rest.api")
public class NotificationProperties {
    private String sendNotificationsUri;
}
