package com.senla.worklog.reminder.employee.domain.port.out;

import com.senla.worklog.reminder.employee.domain.model.Region;

import java.util.UUID;

public interface RegionJpaPort {
    Region addRegion(Region region);

    Region updateRegion(Region region);

    void deleteRegionById(UUID id);
}
