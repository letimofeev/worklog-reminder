package com.senla.worklog.reminder.worklogdebtnotification.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "worklog-debt.rest.api")
public class WorklogDebtsProperties {
    private String getWorklogDebtsUri;
}
