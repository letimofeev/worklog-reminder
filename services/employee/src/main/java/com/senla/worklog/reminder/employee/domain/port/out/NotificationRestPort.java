package com.senla.worklog.reminder.employee.domain.port.out;

import com.senla.worklog.reminder.employee.domain.model.Employee;

/**
 * This interface defines the methods for interacting with employee data in a notification service through a REST API.
 */
public interface NotificationRestPort {
    /**
     * Gets the employee data provided by notification service.
     *
     * @param employee the employee details used to determine the notification recipient
     * @return the employee to whom the notification should be sent, or null if none found
     */
    Employee getNotificationEmployee(Employee employee);

    /**
     * Updates the details of an employee in the notification service.
     *
     * @param employee the employee to update
     * @return the updated employee
     */
    Employee updateEmployee(Employee employee);

    /**
     * Deletes an employee from the notification service.
     *
     * @param employee the employee to delete
     */
    void deleteEmployee(Employee employee);
}
