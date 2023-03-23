package com.senla.worklog.reminder.employee.adapter.out.jpa.mapper;

import com.senla.worklog.reminder.employee.adapter.out.jpa.entity.EmployeeEntity;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import org.mapstruct.Mapper;

/**
 * The {@code EmployeeEntityMapper} interface is responsible for mapping between
 * {@link Employee} domain objects and {@link EmployeeEntity} JPA entities.
 *
 * <p>This interface uses the {@link Mapper} annotation from the MapStruct library,
 * which generates the implementation of the mapper methods at compile time.</p>
 *
 * @since 0.0.1
 */
@Mapper(componentModel = "spring")
public interface EmployeeEntityMapper {

    /**
     * Maps an {@link Employee} domain object to an {@link EmployeeEntity} JPA entity.
     *
     * @param employee the domain object to be mapped
     * @return the JPA entity that was mapped from the domain object
     */
    EmployeeEntity mapToJpaEntity(Employee employee);

    /**
     * Maps an {@link EmployeeEntity} JPA entity to an {@link Employee} domain object.
     *
     * @param employeeEntity the JPA entity to be mapped
     * @return the domain object that was mapped from the JPA entity
     */
    Employee mapToDomain(EmployeeEntity employeeEntity);
}
