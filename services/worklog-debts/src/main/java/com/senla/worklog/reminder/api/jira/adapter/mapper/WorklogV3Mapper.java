package com.senla.worklog.reminder.api.jira.adapter.mapper;

import com.senla.worklog.reminder.model.Worklog;
import com.senla.worklog.reminder.api.jira.v3.model.WorklogV3;

public interface WorklogV3Mapper {
    WorklogV3 mapToV3(Worklog worklog);

    Worklog mapToModel(WorklogV3 worklogV3);
}
