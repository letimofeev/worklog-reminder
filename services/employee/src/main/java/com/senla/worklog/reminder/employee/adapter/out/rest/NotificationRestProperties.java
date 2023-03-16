package com.senla.worklog.reminder.employee.adapter.out.rest;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "notification.rest.api")
public class NotificationRestProperties {
    private String baseUrl;
    private String getUsersByLoginsUri;
    private String updateUserUri;
    private String deleteUserByLoginUri;
}
