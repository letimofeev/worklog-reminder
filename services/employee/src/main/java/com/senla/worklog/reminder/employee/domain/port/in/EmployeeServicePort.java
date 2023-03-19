package com.senla.worklog.reminder.employee.domain.port.in;


import com.senla.worklog.reminder.employee.domain.model.Employee;

import java.util.List;

public interface EmployeeServicePort {
    Employee addEmployee(Employee employee);

    Employee getEmployeeById(Long id);

    Employee getEmployeeByJiraKey(String jiraKey);

    List<Employee> getAllEmployees();

    Employee updateEmployee(Employee employee);

    void deleteEmployeeById(Long id);
}
