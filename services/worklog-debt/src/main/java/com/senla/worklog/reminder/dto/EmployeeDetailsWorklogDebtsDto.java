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
public class EmployeeDetailsWorklogDebtsDto {
    private EmployeeDetailsDto employeeDetails;
    private List<DayWorklogDebtDto> worklogDebts;

    @Override
    public String toString() {
        return "EmployeeDetailsWorklogDebtsDto{" +
                "employeeDetails=" + employeeDetails +
                ", worklogDebts=" + worklogDebts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeDetailsWorklogDebtsDto that = (EmployeeDetailsWorklogDebtsDto) o;

        if (!Objects.equals(employeeDetails, that.employeeDetails))
            return false;
        return Objects.equals(worklogDebts, that.worklogDebts);
    }

    @Override
    public int hashCode() {
        int result = employeeDetails != null ? employeeDetails.hashCode() : 0;
        result = 31 * result + (worklogDebts != null ? worklogDebts.hashCode() : 0);
        return result;
    }
}
