package com.senla.worklog.reminder.worklogdebtnotification.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DayWorklogDebt {
    private LocalDate date;
    private Long timeDeptSeconds;
}
