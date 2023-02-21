package com.senla.worklog.reminder.service;

import com.senla.worklog.reminder.api.v3.model.AuthorV3;
import com.senla.worklog.reminder.api.v3.model.WorklogV3;
import com.senla.worklog.reminder.api.JiraWorklogApiClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PreviousWeekAuthorsFetchStrategy implements AuthorsFetchStrategy {
    private final JiraWorklogApiClient jiraWorklogApiClient;

    public PreviousWeekAuthorsFetchStrategy(JiraWorklogApiClient jiraWorklogApiClient) {
        this.jiraWorklogApiClient = jiraWorklogApiClient;
    }

    @Override
    public List<AuthorV3> getAuthors() {
        List<WorklogV3> worklogs = jiraWorklogApiClient.getAllForPreviousWeek();
        return worklogs.stream()
                .map(WorklogV3::getAuthor)
                .distinct()
                .collect(Collectors.toList());
    }
}
