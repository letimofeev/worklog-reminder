package com.senla.worklog.reminder.employee.application.service.mapper;

import com.senla.worklog.reminder.employee.domain.model.Employee;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeServiceMapper {
    private final ModelMapper mapper;

    public Employee mergeDomains(Employee employee1, Employee employee2) {
        Employee mergedEmployee = new Employee();

        mapper.map(employee1, mergedEmployee);
        mapper.map(employee2, mergedEmployee);

        return mergedEmployee;
    }

    public Employee mergeDomains(Employee... employees) {
        Employee mergedEmployee = new Employee();

        for (Employee employee : employees) {
            mapper.map(employee, mergedEmployee);
        }
        return mergedEmployee;
    }
}
