package com.senla.worklog.reminder.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jira.properties")
public class JiraProperties {
    private String worklogsUrlTemplate;
    private String username;
    private String password;
}
