package com.senla.worklog.reminder.model;

import com.senla.worklog.reminder.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeWorklogDebt {
    private EmployeeDto employee;
    private List<DayWorklogDebt> dayWorklogDebts;

    @Override
    public String toString() {
        return "EmployeeWorklogDebt{" +
                "employee=" + employee +
                ", dayWorklogDebts=" + dayWorklogDebts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeWorklogDebt that = (EmployeeWorklogDebt) o;

        if (!Objects.equals(employee, that.employee)) return false;
        return Objects.equals(dayWorklogDebts, that.dayWorklogDebts);
    }

    @Override
    public int hashCode() {
        int result = employee != null ? employee.hashCode() : 0;
        result = 31 * result + (dayWorklogDebts != null ? dayWorklogDebts.hashCode() : 0);
        return result;
    }
}
