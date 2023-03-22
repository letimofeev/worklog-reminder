package com.senla.worklog.reminder.employee.domain.port.in;


import com.senla.worklog.reminder.employee.domain.model.Employee;

import java.util.List;

/**
 * Primary port interface is used to manage employees
 */
public interface EmployeeServicePort {

    /**
     * Adds an employee to the system.
     *
     * @param employee the employee to add
     * @return the added employee
     */
    Employee addEmployee(Employee employee);

    /**
     * Gets an employee by their ID.
     *
     * @param id the ID of the employee to retrieve
     * @return the employee with the specified ID, or null if not found
     */
    Employee getEmployeeById(Long id);

    /**
     * Gets a list of employees by their Jira key.
     *
     * @param jiraKey the Jira key to search for
     * @return a list of employees with the specified Jira key, or an empty list if none found
     */
    List<Employee> getEmployeesByJiraKey(String jiraKey);

    /**
     * Gets a list of all employees in the system.
     *
     * @return a list of all employees
     */
    List<Employee> getAllEmployees();

    /**
     * Updates an employee in the system.
     *
     * @param employee the employee to update
     * @return the updated employee
     */
    Employee updateEmployee(Employee employee);

    /**
     * Deletes an employee by their ID.
     *
     * @param id the ID of the employee to delete
     */
    void deleteEmployeeById(Long id);
}
