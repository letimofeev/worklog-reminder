package com.senla.worklog.reminder.model.mapper;

import com.senla.worklog.reminder.model.v3.Author;
import com.senla.worklog.reminder.model.v3.Issue;
import com.senla.worklog.reminder.model.v3.IssueType;
import com.senla.worklog.reminder.model.v3.WorklogV3;
import com.senla.worklog.reminder.model.v4.WorklogV4;
import org.springframework.stereotype.Component;

@Component
public class WorklogMapperImpl implements WorklogMapper {
    @Override
    public WorklogV3 mapToV3(WorklogV4 worklogV4) {
        return new WorklogV3()
                .setId(worklogV4.getTempoWorklogId())
                .setJiraWorklogId(worklogV4.getTempoWorklogId())
                .setTimeSpentSeconds(worklogV4.getTimeSpentSeconds())
                .setComment(worklogV4.getComment())
                .setDateStarted(worklogV4.getStarted())
                .setDateUpdated(worklogV4.getDateUpdated())
                .setDateCreated(worklogV4.getDateCreated())
                .setAuthor(new Author()
                        .setKey(worklogV4.getWorker()))
                .setIssue(new Issue()
                        .setId(worklogV4.getIssue().getId())
                        .setKey(worklogV4.getIssue().getKey())
                        .setIssueType(new IssueType()
                                .setName(worklogV4.getIssue().getIssueType()))
                        .setProjectId(worklogV4.getIssue().getProjectId())
                        .setSummary(worklogV4.getIssue().getSummary()));
    }
}
