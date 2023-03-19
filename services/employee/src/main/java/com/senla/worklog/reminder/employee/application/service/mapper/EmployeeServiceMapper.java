package com.senla.worklog.reminder.employee.application.service.mapper;

import com.senla.worklog.reminder.employee.domain.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeServiceMapper {
    @Mapping(target = "id", source = "jpaEmployee.id")
    @Mapping(target = "firstName", source = "jpaEmployee.firstName")
    @Mapping(target = "lastName", source = "jpaEmployee.lastName")
    @Mapping(target = "jiraKey", source = "jpaEmployee.jiraKey")
    @Mapping(target = "skypeLogin", source = "jpaEmployee.skypeLogin")
    @Mapping(target = "notificationEnabled", source = "restEmployee.notificationEnabled")
    @Mapping(target = "botConnected", source = "restEmployee.botConnected")
    Employee mergeDomains(Employee jpaEmployee, Employee restEmployee);
}
