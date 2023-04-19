package com.senla.worklog.reminder.worklogdebt.adapter.out.rest.vacation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VacationDto {
    private LocalDate date;
    private String name;
}
