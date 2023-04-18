package com.senla.worklog.reminder.vacation.service;

import com.senla.worklog.reminder.vacation.model.Region;

import java.util.UUID;

public interface RegionService {
    Region addRegion(Region region);

    Region updateRegion(Region region);

    void deleteRegionById(UUID id);
}
