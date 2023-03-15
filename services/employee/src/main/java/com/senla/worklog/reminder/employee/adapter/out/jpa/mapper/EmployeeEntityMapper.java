package com.senla.worklog.reminder.employee.adapter.out.jpa.mapper;

import com.senla.worklog.reminder.employee.adapter.out.jpa.entity.EmployeeEntity;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeEntityMapper {
    private final ModelMapper mapper;

    public EmployeeEntity mapToJpaEntity(Employee employee) {
        return mapper.map(employee, EmployeeEntity.class);
    }

    public Employee mapToDomain(EmployeeEntity employeeEntity) {
        return mapper.map(employeeEntity, Employee.class);
    }
}
