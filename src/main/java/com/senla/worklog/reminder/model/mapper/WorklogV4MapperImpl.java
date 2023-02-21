package com.senla.worklog.reminder.model.mapper;

import com.senla.worklog.reminder.api.v4.model.IssueV4;
import com.senla.worklog.reminder.api.v4.model.WorklogV4;
import com.senla.worklog.reminder.model.Issue;
import com.senla.worklog.reminder.model.Worker;
import com.senla.worklog.reminder.model.Worklog;
import org.springframework.stereotype.Component;

@Component
public class WorklogV4MapperImpl implements WorklogV4Mapper {
    @Override
    public WorklogV4 mapToV4(Worklog worklog) {
        return new WorklogV4()
                .setTempoWorklogId(worklog.getId())
                .setOriginId(worklog.getId())
                .setComment(worklog.getComment())
                .setTimeSpentSeconds(worklog.getTimeSpentSeconds())
                .setStarted(worklog.getDateStarted().atTime(0, 0, 0))
                .setIssue(new IssueV4()
                        .setId(worklog.getIssue().getId())
                        .setProjectId(worklog.getIssue().getProjectId())
                        .setIssueType(worklog.getIssue().getIssueType())
                        .setKey(worklog.getIssue().getKey())
                        .setSummary(worklog.getIssue().getSummary()))
                .setWorker(worklog.getWorker().getKey());
    }

    @Override
    public Worklog mapToModel(WorklogV4 worklogV4) {
        return new Worklog()
                .setId(worklogV4.getTempoWorklogId())
                .setDateStarted(worklogV4.getStarted().toLocalDate())
                .setTimeSpentSeconds(worklogV4.getTimeSpentSeconds())
                .setComment(worklogV4.getComment())
                .setIssue(new Issue()
                        .setKey(worklogV4.getIssue().getKey())
                        .setProjectId(worklogV4.getIssue().getProjectId())
                        .setId(worklogV4.getIssue().getId())
                        .setSummary(worklogV4.getIssue().getSummary())
                        .setIssueType(worklogV4.getIssue().getIssueType()))
                .setWorker(new Worker()
                        .setKey(worklogV4.getWorker())
                        .setDisplayName(worklogV4.getWorker()));
    }
}
