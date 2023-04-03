package com.senla.worklog.reminder.employee.application.service.mapper;

import com.senla.worklog.reminder.employee.domain.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for merging two {@link Employee} objects from different ports
 */
@Mapper(componentModel = "spring")
public interface EmployeeServiceMapper {

    /**
     * Maps fields from {@code jpaEmployee} and {@code restEmployee} to a new {@code Employee} domain model
     *
     * @param jpaEmployee the {@link Employee} object retrieved from the JPA port
     * @param restEmployee the {@link Employee} object retrieved from the REST API layer
     * @return a new {@link Employee} object with fields from both input objects
     */
    @Mapping(target = "id", source = "jpaEmployee.id")
    @Mapping(target = "firstName", source = "jpaEmployee.firstName")
    @Mapping(target = "lastName", source = "jpaEmployee.lastName")
    @Mapping(target = "jiraKey", source = "jpaEmployee.jiraKey")
    @Mapping(target = "skypeLogin", source = "jpaEmployee.skypeLogin")
    @Mapping(target = "region", source = "jpaEmployee.region")
    @Mapping(target = "notificationEnabled", source = "restEmployee.notificationEnabled")
    @Mapping(target = "botConnected", source = "restEmployee.botConnected")
    Employee mergeDomains(Employee jpaEmployee, Employee restEmployee);
}
