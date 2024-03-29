package com.senla.worklog.reminder.employee.application.service;

import com.senla.worklog.reminder.employee.application.service.mapper.EmployeeServiceMapper;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import com.senla.worklog.reminder.employee.domain.port.in.EmployeeServicePort;
import com.senla.worklog.reminder.employee.domain.port.out.EmployeeJpaPort;
import com.senla.worklog.reminder.employee.domain.port.out.NotificationRestPort;
import com.senla.worklog.reminder.employee.domain.service.EmployeeDomainService;
import com.senla.worklog.reminder.exception.ApplicationException;
import com.senla.worklog.reminder.exception.wrapper.ExceptionWrapperAspect;
import com.senla.worklog.reminder.exception.wrapper.annotation.WrappedInApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

/**
 * This class implements the {@link EmployeeServicePort} interface and provides
 * methods to interact with employee domain models. It is annotated with {@link WrappedInApplicationException}
 * to indicate that all exceptions thrown by this service will be wrapped by {@link ExceptionWrapperAspect}
 * in a corresponding {@link ApplicationException}.
 */
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
        domainService.checkUniqueConstraints(employee);

        var createdEmployee = employeeJpaPort.addEmployee(employee);
        var restEmployee = notificationRestPort.getNotificationEmployee(createdEmployee);

        log.debug("Created employee with id = '{}'", createdEmployee.getId());
        return mapper.mergeDomains(createdEmployee, restEmployee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        var jpaEmployee = employeeJpaPort.getEmployeeById(id);
        var restEmployee = notificationRestPort.getNotificationEmployee(jpaEmployee);

        return mapper.mergeDomains(jpaEmployee, restEmployee);
    }

    @Override
    public List<Employee> getEmployeesByJiraKey(String jiraKey) {
        var jpaEmployees = employeeJpaPort.getEmployeesByJiraKey(jiraKey);
        if (jpaEmployees.isEmpty()) {
            return emptyList();
        }
        var jpaEmployee = jpaEmployees.get(0);
        var restEmployee = notificationRestPort.getNotificationEmployee(jpaEmployee);
        return singletonList(mapper.mergeDomains(jpaEmployee, restEmployee));
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
        domainService.checkUniqueConstraints(employee);

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
