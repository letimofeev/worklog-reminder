package com.senla.worklog.reminder.service.employee;

import com.senla.worklog.reminder.dto.EmployeeDto;
import com.senla.worklog.reminder.dto.mapper.EmployeeDtoMapper;
import com.senla.worklog.reminder.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository repository;
    private final EmployeeDtoMapper mapper;

    @Override
    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        var employee = mapper.mapToModel(employeeDto);
        repository.save(employee);
        return mapper.mapToDto(employee);
    }

    @Override
    public Optional<EmployeeDto> getEmployeeById(Long id) {
        return repository.findById(id)
                .map(mapper::mapToDto);
    }

    @Override
    public Optional<EmployeeDto> getEmployeeByJiraKey(String jiraKey) {
        return repository.findByJiraKey(jiraKey)
                .map(mapper::mapToDto);
    }

    @Override
    public List<EmployeeDto> getAllEmployeeByJiraKeys(List<String> jiraKeys) {
        return repository.findAllByJiraKeyIn(jiraKeys).stream()
                .map(mapper::mapToDto)
                .collect(toList());
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return repository.findAll().stream()
                .map(mapper::mapToDto)
                .collect(toList());
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        var employee = mapper.mapToModel(employeeDto);
        repository.save(employee);
        return mapper.mapToDto(employee);
    }

    @Override
    public void deleteEmployeeById(Long id) {
        repository.deleteById(id);
    }
}
