package com.senla.worklog.reminder.vacation.dto;

import com.senla.worklog.reminder.vacation.model.Vacation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VacationsResponse {
    private Long employeeId;
    private List<Vacation> vacations;
}
