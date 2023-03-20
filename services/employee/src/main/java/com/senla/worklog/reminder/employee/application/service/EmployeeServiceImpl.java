package com.senla.worklog.reminder.employee.application.service;

import com.senla.worklog.reminder.employee.application.annotation.WrappedInApplicationException;
import com.senla.worklog.reminder.employee.application.service.mapper.EmployeeServiceMapper;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import com.senla.worklog.reminder.employee.domain.port.in.EmployeeServicePort;
import com.senla.worklog.reminder.employee.domain.port.out.EmployeeJpaPort;
import com.senla.worklog.reminder.employee.domain.port.out.NotificationRestPort;
import com.senla.worklog.reminder.employee.domain.service.EmployeeDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@WrappedInApplicationException
public class EmployeeServiceImpl implements EmployeeServicePort {
    private final EmployeeJpaPort employeeJpaPort;
    private final NotificationRestPort notificationRestPort;
    private final EmployeeServiceMapper mapper;
    private final EmployeeDomainService domainService;

    @Override
    public Employee addEmployee(Employee employee) {
        domainService.checkUniqueContraints(employee);

        var createdEmployee = employeeJpaPort.addEmployee(employee);
        log.debug("Created employee with id = '{}'", createdEmployee.getId());
        return createdEmployee;
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

        log.debug("Updated employee with id = '{}'", employee.getId());
        return mapper.mergeDomains(jpaEmployee, restEmployee);
    }

    @Override
    public void deleteEmployeeById(Long id) {
        var jpaEmployee = employeeJpaPort.getEmployeeById(id);
        employeeJpaPort.deleteEmployeeById(id);
        notificationRestPort.deleteEmployee(jpaEmployee);

        log.debug("Deleted employee with id = '{}'", id);
    }
}
