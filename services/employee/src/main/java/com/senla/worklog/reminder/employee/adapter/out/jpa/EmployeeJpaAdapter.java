package com.senla.worklog.reminder.employee.adapter.out.jpa;

import com.senla.worklog.reminder.employee.adapter.annotation.DrivenAdapter;
import com.senla.worklog.reminder.employee.adapter.out.jpa.mapper.EmployeeEntityMapper;
import com.senla.worklog.reminder.employee.adapter.out.jpa.repository.EmployeeJpaRepository;
import com.senla.worklog.reminder.employee.domain.exception.EmployeeNotFoundException;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import com.senla.worklog.reminder.employee.domain.port.out.EmployeeJpaPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * This class implements the {@link EmployeeJpaPort} interface and provides the implementation for the CRUD operations
 * on employee entities in the database using JPA.
 */
@DrivenAdapter
@RequiredArgsConstructor
public class EmployeeJpaAdapter implements EmployeeJpaPort {
    private final EmployeeJpaRepository employeeRepository;
    private final EmployeeEntityMapper entityMapper;

    @Override
    public Employee addEmployee(Employee employee) {
        var employeeEntity = entityMapper.mapToJpaEntity(employee);
        employeeRepository.save(employeeEntity);
        return entityMapper.mapToDomain(employeeEntity);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        var employeeEntity = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return entityMapper.mapToDomain(employeeEntity);
    }

    @Override
    public List<Employee> getEmployeesByJiraKey(String jiraKey) {
        var employeesEntities = employeeRepository.findByJiraKey(jiraKey)
                .map(List::of)
                .orElse(List.of());
        return employeesEntities.stream()
                .map(entityMapper::mapToDomain)
                .collect(toList());
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(entityMapper::mapToDomain)
                .collect(toList());
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        var id = employee.getId();
        if (employeeRepository.existsById(id)) {
            var employeeEntity = entityMapper.mapToJpaEntity(employee);
            employeeRepository.save(employeeEntity);
            return entityMapper.mapToDomain(employeeEntity);
        }
        throw new EmployeeNotFoundException(id);
    }

    @Override
    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public boolean existsByJiraKey(String jiraKey) {
        return employeeRepository.existsByJiraKey(jiraKey);
    }

    @Override
    public boolean existsBySkypeLogin(String skypeLogin) {
        return employeeRepository.existsBySkypeLogin(skypeLogin);
    }
}
