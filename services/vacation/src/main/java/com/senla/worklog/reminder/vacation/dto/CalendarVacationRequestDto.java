package com.senla.worklog.reminder.vacation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarVacationRequestDto {
    @NotNull(message = "Calendar vacation date must be specified")
    private LocalDate date;

    @NotNull(message = "Calendar vacation name must be specified")
    private String name;

    @NotNull(message = "Calendar vacation regionId must be specified")
    private UUID regionId;
}
