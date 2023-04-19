package com.senla.worklog.reminder.worklogdebt.adapter.out.rest.vacation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeVacationsDto {
    private Long employeeId;
    private List<VacationDto> vacations;
}
