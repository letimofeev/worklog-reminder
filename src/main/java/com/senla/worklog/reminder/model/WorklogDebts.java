package com.senla.worklog.reminder.model;

import com.senla.worklog.reminder.dto.EmployeeDto;
import lombok.experimental.Delegate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorklogDebts {
    @Delegate
    private final Map<EmployeeDto, List<DayWorklogDebt>> employeesDebts = new HashMap<>();

    @Override
    public String toString() {
        return "WorklogDebts{" +
                "employeesDebts=" + employeesDebts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorklogDebts that = (WorklogDebts) o;

        return employeesDebts.equals(that.employeesDebts);
    }

    @Override
    public int hashCode() {
        return employeesDebts.hashCode();
    }
}
