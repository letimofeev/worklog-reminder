package com.senla.worklog.reminder.config;

import com.senla.worklog.reminder.proxy.BasicAuthJiraWorklogProxy;
import com.senla.worklog.reminder.proxy.JiraWorklogProxy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class JiraWorklogProxyConfig {
    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public JiraWorklogProxy jiraWorklogProxy(RestTemplate restTemplate, JiraProperties jiraProperties) {
        return new BasicAuthJiraWorklogProxy(restTemplate, jiraProperties);
    }
}
