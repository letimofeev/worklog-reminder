package com.senla.worklog.reminder.employee.adapter.in.rest;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.EmployeeDto;
import com.senla.worklog.reminder.employee.adapter.in.rest.mapper.EmployeeRestMapper;
import com.senla.worklog.reminder.employee.domain.port.in.EmployeeServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.ResponseEntity.created;

@CrossOrigin
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeServicePort employeeServicePort;
    private final EmployeeRestMapper employeeMapper;

    @PostMapping
    public ResponseEntity<EmployeeDto> addEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        var employee = employeeMapper.mapToDomain(employeeDto);
        var createdEmployee = employeeServicePort.addEmployee(employee);
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(createdEmployee.getId())
                .toUri();
        var employeeResponse = employeeMapper.mapToDto(employee);
        return created(location).body(employeeResponse);
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployeeById(@PathVariable Long id) {
        var employee = employeeServicePort.getEmployeeById(id);
        return employeeMapper.mapToDto(employee);
    }

    @GetMapping(params = "jiraKey")
    public EmployeeDto getEmployeeByJiraKey(@RequestParam String jiraKey) {
        var employee = employeeServicePort.getEmployeeByJiraKey(jiraKey);
        return employeeMapper.mapToDto(employee);
    }

    @GetMapping
    public List<EmployeeDto> getAllEmployees() {
        return employeeServicePort.getAllEmployees().stream()
                .map(employeeMapper::mapToDto)
                .collect(toList());
    }

    @PutMapping
    public EmployeeDto updateEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        var employee = employeeMapper.mapToDomain(employeeDto);
        var updatedEmployee = employeeServicePort.updateEmployee(employee);
        return employeeMapper.mapToDto(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteEmployeeById(@PathVariable Long id) {
        employeeServicePort.deleteEmployeeById(id);
    }
}
