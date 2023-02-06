package com.senla.worklog.reminder.config;

import com.senla.worklog.reminder.proxy.BasicAuthJiraWorklogProxy;
import com.senla.worklog.reminder.proxy.JiraWorklogProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JiraWorklogProxyConfig {
    @Bean
    public JiraWorklogProxy jiraWorklogProxy(JiraProperties jiraProperties) {
        return new BasicAuthJiraWorklogProxy(jiraProperties);
    }
}
