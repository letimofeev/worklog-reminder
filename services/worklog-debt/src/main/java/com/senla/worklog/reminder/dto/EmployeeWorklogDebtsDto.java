package com.senla.worklog.reminder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EmployeeWorklogDebtsDto {
    private EmployeeDto employee;
    private List<DayWorklogDebtDto> worklogDebts;

    @Override
    public String toString() {
        return "EmployeeWorklogDebtsDto{" +
                "employee=" + employee +
                ", worklogDebts=" + worklogDebts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeWorklogDebtsDto that = (EmployeeWorklogDebtsDto) o;

        if (!Objects.equals(employee, that.employee)) return false;
        return Objects.equals(worklogDebts, that.worklogDebts);
    }

    @Override
    public int hashCode() {
        int result = employee != null ? employee.hashCode() : 0;
        result = 31 * result + (worklogDebts != null ? worklogDebts.hashCode() : 0);
        return result;
    }
}
