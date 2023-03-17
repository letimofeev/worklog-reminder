package com.senla.worklog.reminder.worklogdebt.domain.port.out;

import com.senla.worklog.reminder.worklogdebt.domain.model.EmployeeWorklogDebts;

public interface EmployeeRestPort {
    EmployeeWorklogDebts getEmployee(EmployeeWorklogDebts worklogDebts);
}
