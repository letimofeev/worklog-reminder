package com.senla.worklog.reminder.model.mapper;

import com.senla.worklog.reminder.api.v3.model.WorklogV3;
import com.senla.worklog.reminder.api.v4.model.WorklogV4;

public interface WorklogMapper {
    WorklogV3 mapToV3(WorklogV4 worklogV4);
}
