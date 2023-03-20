package com.senla.worklog.reminder.employee.domain.port.out;

import com.senla.worklog.reminder.employee.domain.model.Employee;

import java.util.List;

public interface EmployeeJpaPort {
    Employee addEmployee(Employee Employee);

    Employee getEmployeeById(Long id);

    Employee getEmployeeByJiraKey(String jiraKey);

    List<Employee> getAllEmployees();

    Employee updateEmployee(Employee employee);

    void deleteEmployeeById(Long id);

    boolean existsByJiraKey(String jiraKey);

    boolean existsBySkypeLogin(String skypeLogin);
}
