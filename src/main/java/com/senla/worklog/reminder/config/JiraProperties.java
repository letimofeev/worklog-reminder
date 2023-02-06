package com.senla.worklog.reminder.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "jira")
public class JiraProperties {
    private String worklogsUrlTemplate;
    private String username;
    private String password;
}
