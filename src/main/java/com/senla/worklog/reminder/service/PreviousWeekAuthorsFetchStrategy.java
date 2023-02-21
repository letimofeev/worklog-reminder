package com.senla.worklog.reminder.service;

import com.senla.worklog.reminder.model.v3.Author;
import com.senla.worklog.reminder.model.v3.WorklogV3;
import com.senla.worklog.reminder.api.client.JiraWorklogApiClient;
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
    public List<Author> getAuthors() {
        List<WorklogV3> worklogs = jiraWorklogApiClient.getAllForPreviousWeek();
        return worklogs.stream()
                .map(WorklogV3::getAuthor)
                .distinct()
                .collect(Collectors.toList());
    }
}
