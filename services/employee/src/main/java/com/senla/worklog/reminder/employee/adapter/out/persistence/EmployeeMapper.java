package com.senla.worklog.reminder.employee.adapter.out.persistence;

import com.senla.worklog.reminder.employee.domain.Employee;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeMapper {
    private final ModelMapper mapper;

    public EmployeeEntity mapToJpaEntity(Employee employee) {
        return mapper.map(employee, EmployeeEntity.class);
    }

    public Employee mapToDomain(EmployeeEntity employeeEntity) {
        return mapper.map(employeeEntity, Employee.class);
    }
}
