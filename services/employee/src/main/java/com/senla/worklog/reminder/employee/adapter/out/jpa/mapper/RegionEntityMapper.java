package com.senla.worklog.reminder.employee.adapter.out.jpa.mapper;

import com.senla.worklog.reminder.employee.adapter.out.jpa.entity.RegionEntity;
import com.senla.worklog.reminder.employee.domain.model.Region;
import org.mapstruct.Mapper;

/**
 * The {@code RegionEntityMapper} interface is responsible for mapping between
 * {@link Region} domain objects and {@link RegionEntity} JPA entities.
 *
 * <p>This interface uses the {@link Mapper} annotation from the MapStruct library,
 * which generates the implementation of the mapper methods at compile time.</p>
 *
 */
@Mapper(componentModel = "spring")
public interface RegionEntityMapper {
    /**
     * Maps an {@link Region} domain object to an {@link RegionEntity} JPA entity.
     *
     * @param region the domain object to be mapped
     * @return the JPA entity that was mapped from the domain object
     */
    RegionEntity mapToJpaEntity(Region region);

    /**
     * Maps an {@link RegionEntity} JPA entity to an {@link Region} domain object.
     *
     * @param regionEntity the JPA entity to be mapped
     * @return the domain object that was mapped from the JPA entity
     */
    Region mapToDomain(RegionEntity regionEntity);
}
