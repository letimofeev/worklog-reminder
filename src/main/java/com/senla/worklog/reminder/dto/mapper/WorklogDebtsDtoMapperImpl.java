package com.senla.worklog.reminder.dto.mapper;

import com.senla.worklog.reminder.dto.EmployeeWorklogDebtsDto;
import com.senla.worklog.reminder.dto.WorklogDebtsDto;
import com.senla.worklog.reminder.model.WorklogDebts;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

@Component
public class WorklogDebtsDtoMapperImpl implements WorklogDebtsDtoMapper {
    @Override
    public WorklogDebts mapToModel(WorklogDebtsDto worklogDebtsDto) {
        var worklogDebts = new WorklogDebts();
        worklogDebtsDto.getWorklogDebts()
                .forEach(dto -> worklogDebts.put(dto.getEmployee(), dto.getWorklogDebts()));
        return worklogDebts;
    }

    @Override
    public WorklogDebtsDto mapToDto(WorklogDebts worklogDebts) {
        var employeeWorklogDebts = worklogDebts.entrySet().stream()
                .map(entry -> new EmployeeWorklogDebtsDto(entry.getKey(), entry.getValue()))
                .collect(toList());
        return new WorklogDebtsDto(employeeWorklogDebts);
    }
}
