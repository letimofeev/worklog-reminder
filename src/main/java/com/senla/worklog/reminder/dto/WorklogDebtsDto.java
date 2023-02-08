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
public class WorklogDebtsDto {
    private List<EmployeeWorklogDebtsDto> worklogDebts;

    @Override
    public String toString() {
        return "WorklogDebtsDto{" +
                "worklogDebts=" + worklogDebts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorklogDebtsDto that = (WorklogDebtsDto) o;

        return Objects.equals(worklogDebts, that.worklogDebts);
    }

    @Override
    public int hashCode() {
        return worklogDebts != null ? worklogDebts.hashCode() : 0;
    }
}
