package com.senla.worklog.reminder.vacation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vacation {
    private Long employeeId;
    private LocalDate date;
    private String name;
}
