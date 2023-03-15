package com.senla.worklog.reminder.employee.application.service;


import com.senla.worklog.reminder.employee.domain.Employee;

import java.util.List;

public interface EmployeeService {
    void addEmployee(Employee employeeDto);

    Employee getEmployeeById(Long id);

    Employee getEmployeeByJiraKey(String jiraKey);

    List<Employee> getAllEmployees();

    void updateEmployee(Employee employeeDto);

    void deleteEmployeeById(Long id);
}
