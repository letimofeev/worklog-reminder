package com.senla.worklog.reminder.controller;

import com.senla.worklog.reminder.dto.EmployeeDto;
import com.senla.worklog.reminder.exception.EmployeeNotFoundException;
import com.senla.worklog.reminder.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto created = employeeService.addEmployee(employeeDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("employees/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployeeById(@PathVariable long id) {
        return employeeService.getEmployeeById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with id = '" + id + "' not found"));
    }

    @GetMapping
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PutMapping
    public EmployeeDto updateEmployee(@RequestBody EmployeeDto accountDto) {
        return employeeService.updateEmployee(accountDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployeeById(@PathVariable long id) {
        employeeService.deleteEmployeeById(id);
    }
}
