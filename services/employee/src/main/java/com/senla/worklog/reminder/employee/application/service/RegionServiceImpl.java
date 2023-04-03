package com.senla.worklog.reminder.employee.application.service;

import com.senla.worklog.reminder.employee.domain.model.Region;
import com.senla.worklog.reminder.employee.domain.port.in.RegionServicePort;
import com.senla.worklog.reminder.employee.domain.port.out.RegionJpaPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionServicePort {
    private final RegionJpaPort regionJpaPort;

    @Override
    public Region addRegion(Region region) {
        return regionJpaPort.addRegion(region);
    }

    @Override
    public Region updateRegion(Region region) {
        return regionJpaPort.updateRegion(region);
    }

    @Override
    public void deleteRegionById(UUID id) {
        regionJpaPort.deleteRegionById(id);
    }
}
