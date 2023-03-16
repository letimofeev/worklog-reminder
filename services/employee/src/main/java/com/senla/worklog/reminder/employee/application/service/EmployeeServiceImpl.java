package com.senla.worklog.reminder.employee.application.service;

import com.senla.worklog.reminder.employee.application.service.mapper.EmployeeServiceMapper;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import com.senla.worklog.reminder.employee.domain.port.in.EmployeeServicePort;
import com.senla.worklog.reminder.employee.domain.port.out.EmployeeJpaPort;
import com.senla.worklog.reminder.employee.domain.port.out.NotificationRestPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeServicePort {
    private final EmployeeJpaPort employeeJpaPort;
    private final NotificationRestPort notificationRestPort;
    private final EmployeeServiceMapper mapper;

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeJpaPort.addEmployee(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        var jpaEmployee = employeeJpaPort.getEmployeeById(id);
        var restEmployee = notificationRestPort.getNotificationEmployee(jpaEmployee);

        return mapper.mergeDomains(jpaEmployee, restEmployee);
    }

    @Override
    public Employee getEmployeeByJiraKey(String jiraKey) {
        var jpaEmployee = employeeJpaPort.getEmployeeByJiraKey(jiraKey);
        var restEmployee = notificationRestPort.getNotificationEmployee(jpaEmployee);

        return mapper.mergeDomains(jpaEmployee, restEmployee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeJpaPort.getAllEmployees().stream()
                .map(jpaEmployee -> {
                    var restEmployee = notificationRestPort.getNotificationEmployee(jpaEmployee);
                    return mapper.mergeDomains(jpaEmployee, restEmployee);
                })
                .collect(toList());
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        var jpaEmployee = employeeJpaPort.updateEmployee(employee);
        var restEmployee = notificationRestPort.updateEmployee(employee);

        return mapper.mergeDomains(jpaEmployee, restEmployee);
    }

    @Override
    public void deleteEmployeeById(Long id) {
        var jpaEmployee = employeeJpaPort.getEmployeeById(id);
        employeeJpaPort.deleteEmployeeById(id);
        notificationRestPort.deleteEmployee(jpaEmployee);
    }
}