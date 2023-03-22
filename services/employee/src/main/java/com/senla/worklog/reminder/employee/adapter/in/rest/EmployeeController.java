package com.senla.worklog.reminder.employee.adapter.in.rest;

import com.senla.worklog.reminder.employee.adapter.annotation.DrivingAdapter;
import com.senla.worklog.reminder.employee.adapter.in.rest.dto.CreateEmployeeRequestDto;
import com.senla.worklog.reminder.employee.adapter.in.rest.dto.EmployeeDto;
import com.senla.worklog.reminder.employee.adapter.in.rest.dto.UpdateEmployeeRequestDto;
import com.senla.worklog.reminder.employee.adapter.in.rest.mapper.EmployeeRestMapper;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import com.senla.worklog.reminder.employee.domain.port.in.EmployeeServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;

/**
 * The EmployeeController class is responsible for handling HTTP requests related to employee data.
 * It is annotated with various Spring annotations to provide additional configuration and functionality
 */
@DrivingAdapter
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class EmployeeController implements EmployeeRestAdapter {
    private final EmployeeServicePort employeeServicePort;
    private final EmployeeRestMapper employeeMapper;

    @Override
    public ResponseEntity<EmployeeDto> addEmployee(CreateEmployeeRequestDto employeeDto) {
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

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        var employee = employeeServicePort.getEmployeeById(id);
        return employeeMapper.mapToDto(employee);
    }

    @Override
    public List<EmployeeDto> getEmployees(String jiraKey) {
        List<Employee> employees;
        if (jiraKey != null) {
            employees = employeeServicePort.getEmployeesByJiraKey(jiraKey);
        } else {
            employees = employeeServicePort.getAllEmployees();
        }
        return employees.stream()
                .map(employeeMapper::mapToDto)
                .collect(toList());
    }

    @Override
    public EmployeeDto updateEmployee(UpdateEmployeeRequestDto employeeDto) {
        var employee = employeeMapper.mapToDomain(employeeDto);
        var updatedEmployee = employeeServicePort.updateEmployee(employee);
        return employeeMapper.mapToDto(updatedEmployee);
    }

    @Override
    public ResponseEntity<?> deleteEmployeeById(Long id) {
        employeeServicePort.deleteEmployeeById(id);
        return noContent().build();
    }
}
