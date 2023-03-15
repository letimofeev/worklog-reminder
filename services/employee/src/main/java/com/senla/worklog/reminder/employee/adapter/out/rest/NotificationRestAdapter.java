package com.senla.worklog.reminder.employee.adapter.out.rest;

import com.senla.worklog.reminder.employee.common.annotation.RestAdapter;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import com.senla.worklog.reminder.employee.domain.port.out.NotificationRestPort;
import lombok.RequiredArgsConstructor;

@RestAdapter
@RequiredArgsConstructor
public class NotificationRestAdapter implements NotificationRestPort {
    @Override
    public Employee getNotificationEmployee(Employee employee) {
        return null;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return null;
    }

    @Override
    public void deleteEmployee(Employee employee) {

    }
}
