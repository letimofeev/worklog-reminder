package com.senla.worklog.reminder.service;

import com.senla.worklog.reminder.model.Author;
import com.senla.worklog.reminder.model.Worklog;
import com.senla.worklog.reminder.proxy.JiraWorklogProxy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PreviousWeekAuthorsFetchStrategy implements AuthorsFetchStrategy {
    private final JiraWorklogProxy jiraWorklogProxy;

    public PreviousWeekAuthorsFetchStrategy(JiraWorklogProxy jiraWorklogProxy) {
        this.jiraWorklogProxy = jiraWorklogProxy;
    }

    @Override
    public List<Author> getAuthors() {
        List<Worklog> worklogs = jiraWorklogProxy.findAllForPreviousWeek();
        return worklogs.stream()
                .map(Worklog::getAuthor)
                .collect(Collectors.toList());
    }
}
