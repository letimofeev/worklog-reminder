package com.senla.worklog.reminder.service;

import com.senla.worklog.reminder.model.Author;
import com.senla.worklog.reminder.model.Worklog;
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
        List<Worklog> worklogs = jiraWorklogApiClient.getAllForPreviousWeek();
        return worklogs.stream()
                .distinct()
                .map(Worklog::getAuthor)
                .collect(Collectors.toList());
    }
}
