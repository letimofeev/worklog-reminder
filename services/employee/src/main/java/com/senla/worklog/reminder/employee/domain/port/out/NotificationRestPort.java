package com.senla.worklog.reminder.employee.domain.port.out;

import com.senla.worklog.reminder.employee.domain.model.Employee;

public interface NotificationRestPort {
    Employee getNotificationEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    void deleteEmployee(Employee employee);
}
