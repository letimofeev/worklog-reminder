package com.senla.worklog.reminder.service;

import com.senla.worklog.reminder.dto.EmployeeDto;
import com.senla.worklog.reminder.dto.mapper.EmployeeDtoMapper;
import com.senla.worklog.reminder.exception.EmployeeNotFoundException;
import com.senla.worklog.reminder.model.Employee;
import com.senla.worklog.reminder.repository.EmployeeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeServiceJpaBackend implements EmployeeService {
    private final EmployeeRepository repository;
    private final EmployeeDtoMapper mapper;

    public EmployeeServiceJpaBackend(EmployeeRepository repository, EmployeeDtoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        Employee employee = mapper.mapToModel(employeeDto);
        repository.save(employee);
        return mapper.mapToDto(employee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        return repository.findById(id)
                .map(mapper::mapToDto)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee wih id = '" + id + "' not found"));
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return repository.findAll().stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        Employee employee = mapper.mapToModel(employeeDto);
        repository.save(employee);
        return mapper.mapToDto(employee);
    }

    @Override
    public void deleteEmployeeById(Long id) {
        repository.deleteById(id);
    }
}
