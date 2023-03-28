package com.senla.worklog.reminder.worklogdebt.adapter.out.rest.vacation;

import com.senla.worklog.reminder.annotation.DrivenAdapter;
import com.senla.worklog.reminder.worklogdebt.domain.model.EmployeeWorklogDebts;
import com.senla.worklog.reminder.worklogdebt.domain.port.out.VacationRestPort;

import java.time.LocalDate;

@DrivenAdapter
public class VacationRestAdapter implements VacationRestPort {

    // TODO: Vacation service integration
    @Override
    public EmployeeWorklogDebts getExcludedDays(EmployeeWorklogDebts worklogDebts, LocalDate dateFrom, LocalDate dateTo) {
        return worklogDebts.setDateFrom(dateFrom)
                .setDateTo(dateTo);
    }
}
