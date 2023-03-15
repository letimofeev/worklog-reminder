package com.senla.worklog.reminder.employee.adapter.in.web;

import com.senla.worklog.reminder.employee.domain.Employee;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeMapper {
    private final ModelMapper mapper;

    public Employee mapToDomain(EmployeeRequest request) {
        return mapper.map(request, Employee.class);
    }

    public Employee mapToDomain(EmployeeResponse employeeDto) {
        return mapper.map(employeeDto, Employee.class);
    }

    public EmployeeRequest mapToRequest(Employee employee) {
        return mapper.map(employee, EmployeeRequest.class);
    }

    public EmployeeResponse mapToResponse(Employee employee) {
        return mapper.map(employee, EmployeeResponse.class);
    }
}
