package com.senla.worklog.reminder.vacation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "calendar_vacations")
public class CalendarVacation {
    @Id
    @NotNull
    private LocalDate date;

    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;
}
