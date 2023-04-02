package com.senla.worklog.reminder.employee.adapter.in.rest.mapper;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.CreateEmployeeRequestDto;
import com.senla.worklog.reminder.employee.adapter.in.rest.dto.EmployeeDto;
import com.senla.worklog.reminder.employee.adapter.in.rest.dto.UpdateEmployeeRequestDto;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * The {@code EmployeeRestMapper} interface is responsible for mapping between
 * domain objects and data transfer objects used by the REST API.
 *
 * <p>This interface uses the {@link Mapper} annotation from the MapStruct library,
 * which generates the implementation of the mapper methods at compile time.</p>
 *
 * @since 0.0.1
 */
@Mapper(componentModel = "spring")
public interface EmployeeRestMapper {

    /**
     * Maps a {@link CreateEmployeeRequestDto} object to an {@link Employee} domain object.
     *
     * @param createEmployeeDto the DTO object to be mapped
     * @return the domain object that was mapped from the DTO
     */
    @Mapping(target = "region.id", source = "regionId")
    Employee mapToDomain(CreateEmployeeRequestDto createEmployeeDto);

    /**
     * Maps an {@link UpdateEmployeeRequestDto} object to an {@link Employee} domain object.
     *
     * @param updateEmployeeDto the DTO object to be mapped
     * @return the domain object that was mapped from the DTO
     */
    @Mapping(target = "region.id", source = "regionId")
    Employee mapToDomain(UpdateEmployeeRequestDto updateEmployeeDto);

    /**
     * Maps an {@link Employee} domain object to an {@link EmployeeDto} DTO object.
     *
     * @param employee the domain object to be mapped
     * @return the DTO object that was mapped from the domain object
     */
    EmployeeDto mapToDto(Employee employee);
}
