package com.senla.worklog.reminder.employee.adapter.out.jpa;

import com.senla.worklog.reminder.employee.adapter.out.jpa.mapper.EmployeeEntityMapper;
import com.senla.worklog.reminder.employee.adapter.out.jpa.repository.EmployeeJpaRepository;
import com.senla.worklog.reminder.employee.domain.port.out.EmployeeJpaPort;
import com.senla.worklog.reminder.employee.common.annotation.JpaAdapter;
import com.senla.worklog.reminder.employee.common.exception.EmployeeNotFoundException;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@JpaAdapter
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
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with id = '" + id + "' not found"));
        return entityMapper.mapToDomain(employeeEntity);
    }

    @Override
    public Employee getEmployeeByJiraKey(String jiraKey) {
        var employeeEntity = employeeRepository.findByJiraKey(jiraKey)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with jiraKey = '" + jiraKey + "' not found"));
        return entityMapper.mapToDomain(employeeEntity);
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
        throw new EmployeeNotFoundException("Employee with id = '" + id + "' not found");
    }

    @Override
    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }
}
