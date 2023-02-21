package com.senla.worklog.reminder.model.mapper;

import com.senla.worklog.reminder.api.v4.model.WorklogV4;
import com.senla.worklog.reminder.model.Worklog;

public interface WorklogV4Mapper {
    WorklogV4 mapToV4(Worklog worklog);

    Worklog mapToModel(WorklogV4 worklogV3);
}
