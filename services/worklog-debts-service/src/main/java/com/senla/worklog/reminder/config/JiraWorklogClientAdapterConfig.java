package com.senla.worklog.reminder.config;

import com.senla.worklog.reminder.api.jira.adapter.JiraWorklogClientAdapter;
import com.senla.worklog.reminder.api.jira.adapter.JiraWorklogClientV4Adapter;
import com.senla.worklog.reminder.api.jira.v4.client.JiraWorklogClientV4;
import com.senla.worklog.reminder.model.mapper.WorklogV4Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JiraWorklogClientAdapterConfig {
    @Bean
    public JiraWorklogClientAdapter jiraWorklogClientAdapter(JiraWorklogClientV4 worklogClientV4, WorklogV4Mapper mapper) {
        return new JiraWorklogClientV4Adapter(worklogClientV4, mapper);
    }
}
