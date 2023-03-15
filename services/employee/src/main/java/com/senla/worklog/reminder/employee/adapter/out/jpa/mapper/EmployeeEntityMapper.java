package com.senla.worklog.reminder.employee.adapter.out.jpa.mapper;

import com.senla.worklog.reminder.employee.adapter.out.jpa.entity.EmployeeEntity;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeEntityMapper {
    EmployeeEntity mapToJpaEntity(Employee employee);

    Employee mapToDomain(EmployeeEntity employeeEntity);
}
