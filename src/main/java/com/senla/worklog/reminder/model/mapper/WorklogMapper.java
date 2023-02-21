package com.senla.worklog.reminder.model.mapper;

import com.senla.worklog.reminder.model.v3.WorklogV3;
import com.senla.worklog.reminder.model.v4.WorklogV4;

public interface WorklogMapper {
    WorklogV3 mapToV3(WorklogV4 worklogV4);
}
