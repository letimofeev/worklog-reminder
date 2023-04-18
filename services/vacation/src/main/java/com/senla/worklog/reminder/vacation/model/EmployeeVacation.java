package com.senla.worklog.reminder.vacation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee_vacations", uniqueConstraints = {
        @UniqueConstraint(name = "uc_employee_vacation_date", columnNames = {"date"})
})
public class EmployeeVacation {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull(message = "Employee id must be specified")
    private Long employeeId;

    @NotNull(message = "Employee vacation date must be specified")
    private LocalDate date;

    @NotNull(message = "Employee vacation name must be specified")
    private String name;
}
