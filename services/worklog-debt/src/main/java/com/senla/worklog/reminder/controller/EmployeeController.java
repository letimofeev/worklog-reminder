package com.senla.worklog.reminder.controller;

import com.senla.worklog.reminder.dto.EmployeeDto;
import com.senla.worklog.reminder.exception.EmployeeNotFoundException;
import com.senla.worklog.reminder.service.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody EmployeeDto employeeDto) {
        var created = employeeService.addEmployee(employeeDto);
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("employees/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return created(location).body(created);
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
    @ResponseStatus(NO_CONTENT)
    public void deleteEmployeeById(@PathVariable long id) {
        employeeService.deleteEmployeeById(id);
    }
}
