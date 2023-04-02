package com.senla.worklog.reminder.employee.domain.port.in;

import com.senla.worklog.reminder.employee.domain.model.Region;

import java.util.UUID;

public interface RegionServicePort {
    Region addRegion(Region region);

    Region updateRegion(Region region);

    void deleteRegionById(UUID id);
}
