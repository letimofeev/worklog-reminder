package com.senla.worklog.reminder.model.mapper;

import com.senla.worklog.reminder.api.jira.v3.model.AuthorV3;
import com.senla.worklog.reminder.model.Issue;
import com.senla.worklog.reminder.model.Worker;
import com.senla.worklog.reminder.model.Worklog;
import com.senla.worklog.reminder.api.jira.v3.model.IssueTypeV3;
import com.senla.worklog.reminder.api.jira.v3.model.IssueV3;
import com.senla.worklog.reminder.api.jira.v3.model.WorklogV3;
import org.springframework.stereotype.Component;

@Component
public class WorklogV3MapperImpl implements WorklogV3Mapper {

    @Override
    public WorklogV3 mapToV3(Worklog worklog) {
        return new WorklogV3()
                .setId(worklog.getId())
                .setJiraWorklogId(worklog.getId())
                .setComment(worklog.getComment())
                .setTimeSpentSeconds(worklog.getTimeSpentSeconds())
                .setDateCreated(worklog.getDateStarted().atTime(0, 0, 0))
                .setIssue(new IssueV3()
                        .setId(worklog.getIssue().getId())
                        .setKey(worklog.getIssue().getKey())
                        .setProjectId(worklog.getIssue().getProjectId())
                        .setSummary(worklog.getIssue().getSummary())
                        .setIssueType(new IssueTypeV3()
                                .setName(worklog.getIssue().getIssueType())))
                .setAuthor(new AuthorV3()
                        .setDisplayName(worklog.getWorker().getDisplayName())
                        .setKey(worklog.getWorker().getKey()));
    }

    @Override
    public Worklog mapToModel(WorklogV3 worklogV3) {
        return new Worklog()
                .setId(worklogV3.getId())
                .setComment(worklogV3.getComment())
                .setTimeSpentSeconds(worklogV3.getTimeSpentSeconds())
                .setDateStarted(worklogV3.getDateStarted().toLocalDate())
                .setIssue(new Issue()
                        .setId(worklogV3.getIssue().getId())
                        .setKey(worklogV3.getIssue().getKey())
                        .setProjectId(worklogV3.getIssue().getProjectId())
                        .setIssueType(worklogV3.getIssue().getIssueType().getName())
                        .setSummary(worklogV3.getIssue().getSummary()))
                .setWorker(new Worker()
                        .setKey(worklogV3.getAuthor().getKey())
                        .setDisplayName(worklogV3.getAuthor().getDisplayName()));
    }
}
