package com.senla.worklog.reminder.employee.adapter.out.jpa;

import com.senla.worklog.reminder.annotation.DrivenAdapter;
import com.senla.worklog.reminder.employee.adapter.out.jpa.mapper.RegionEntityMapper;
import com.senla.worklog.reminder.employee.adapter.out.jpa.repository.RegionJpaRepository;
import com.senla.worklog.reminder.employee.domain.model.Region;
import com.senla.worklog.reminder.employee.domain.port.out.RegionJpaPort;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@DrivenAdapter
@RequiredArgsConstructor
public class RegionJpaAdapter implements RegionJpaPort {
    private final RegionJpaRepository regionRepository;
    private final RegionEntityMapper entityMapper;

    @Override
    public Region addRegion(Region region) {
        return saveRegionInternal(region);
    }

    @Override
    public Region updateRegion(Region region) {
        return saveRegionInternal(region);
    }

    @Override
    public void deleteRegionById(UUID id) {
        if (regionRepository.existsById(id)) {
            regionRepository.deleteById(id);
        }
    }

    private Region saveRegionInternal(Region region) {
        var regionEntity = entityMapper.mapToJpaEntity(region);
        var savedRegion = regionRepository.save(regionEntity);
        return entityMapper.mapToDomain(savedRegion);
    }
}
