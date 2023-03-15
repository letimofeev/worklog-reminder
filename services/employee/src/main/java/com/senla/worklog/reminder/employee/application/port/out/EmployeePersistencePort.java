package com.senla.worklog.reminder.employee.application.port.out;

import com.senla.worklog.reminder.employee.domain.Employee;

import java.util.List;

public interface EmployeePersistencePort {
    void addEmployee(Employee Employee);

    Employee getEmployeeById(Long id);

    Employee getEmployeeByJiraKey(String jiraKey);

    List<Employee> getAllEmployees();

    void updateEmployee(Employee employee);

    void deleteEmployeeById(Long id);
}
