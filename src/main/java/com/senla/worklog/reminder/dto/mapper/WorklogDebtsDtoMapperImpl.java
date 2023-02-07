package com.senla.worklog.reminder.dto.mapper;

import com.senla.worklog.reminder.dto.EmployeeDto;
import com.senla.worklog.reminder.dto.EmployeeWorklogDebtsDto;
import com.senla.worklog.reminder.dto.WorklogDebtsDto;
import com.senla.worklog.reminder.model.DayWorklogDebt;
import com.senla.worklog.reminder.model.WorklogDebts;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WorklogDebtsDtoMapperImpl implements WorklogDebtsDtoMapper {
    @Override
    public WorklogDebts mapToModel(WorklogDebtsDto worklogDebtsDto) {
        WorklogDebts worklogDebts = new WorklogDebts();
        worklogDebtsDto.getWorklogDebts()
                .forEach(dto -> worklogDebts.put(dto.getEmployee(), dto.getWorklogDebts()));
        return worklogDebts;
    }

    @Override
    public WorklogDebtsDto mapToDto(WorklogDebts worklogDebts) {
        List<EmployeeWorklogDebtsDto> employeeWorklogDebts = worklogDebts.entrySet().stream()
                .map(entry -> {
                    EmployeeDto employee = entry.getKey();
                    List<DayWorklogDebt> dayWorklogDebts = entry.getValue();
                    return new EmployeeWorklogDebtsDto(employee, dayWorklogDebts);
                })
                .collect(Collectors.toList());
        return new WorklogDebtsDto(employeeWorklogDebts);
    }
}
