package com.senla.worklog.reminder.employee.adapter.in.web;

import com.senla.worklog.reminder.employee.application.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @PostMapping
    public ResponseEntity<EmployeeResponse> addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        var employee = employeeMapper.mapToDomain(employeeRequest);
        employeeService.addEmployee(employee);
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("employees/{id}")
                .buildAndExpand(employee.getId())
                .toUri();
        var employeeResponse = employeeMapper.mapToResponse(employee);
        return created(location).body(employeeResponse);
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable Long id) {
        var employee = employeeService.getEmployeeById(id);
        return employeeMapper.mapToResponse(employee);
    }

    public EmployeeResponse getEmployeeByJiraKey(@PathVariable) {

    }

    @GetMapping
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees().stream()
                .map(employeeMapper::mapToResponse)
                .collect(toList());
    }

    @PutMapping
    public EmployeeResponse updateEmployee(@RequestBody EmployeeRequest employeeDto) {
        var employee = employeeMapper.mapToDomain(employeeDto);
        employeeService.updateEmployee(employee);
        return employeeMapper.mapToResponse(employee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
    }
}
