package com.senla.worklog.reminder.dto.mapper;

import com.senla.worklog.reminder.dto.WorklogDebtsDto;
import com.senla.worklog.reminder.model.WorklogDebts;

public interface WorklogDebtsDtoMapper {
    WorklogDebts mapToModel(WorklogDebtsDto worklogDebtsDto);

    WorklogDebtsDto mapToDto(WorklogDebts worklogDebts);
}
