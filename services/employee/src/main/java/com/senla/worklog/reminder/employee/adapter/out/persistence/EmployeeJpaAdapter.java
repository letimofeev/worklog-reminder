package com.senla.worklog.reminder.employee.adapter.out.persistence;

import com.senla.worklog.reminder.employee.application.port.out.EmployeePersistencePort;
import com.senla.worklog.reminder.employee.infrastructure.annotation.PersistenceAdapter;
import com.senla.worklog.reminder.employee.infrastructure.exception.EmployeeNotFoundException;
import com.senla.worklog.reminder.employee.domain.Employee;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@PersistenceAdapter
@RequiredArgsConstructor
public class EmployeeJpaAdapter implements EmployeePersistencePort {
    private final EmployeeJpaRepository employeeRepository;
    private final EmployeeEntityMapper entityMapper;

    @Override
    public void addEmployee(Employee employee) {
        var employeeEntity = entityMapper.mapToJpaEntity(employee);
        employeeRepository.save(employeeEntity);
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
    public void updateEmployee(Employee employee) {
        var id = employee.getId();
        if (employeeRepository.existsById(id)) {
            var employeeEntity = entityMapper.mapToJpaEntity(employee);
            employeeRepository.save(employeeEntity);
        }
        throw new EmployeeNotFoundException("Employee with id = '" + id + "' not found");
    }

    @Override
    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }
}
