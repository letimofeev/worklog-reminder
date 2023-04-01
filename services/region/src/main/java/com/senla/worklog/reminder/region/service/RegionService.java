package com.senla.worklog.reminder.region.service;

import com.senla.worklog.reminder.region.model.Region;

import java.util.List;
import java.util.UUID;

public interface RegionService {
    List<Region> getAllRegions();

    Region getRegionById(UUID id);

    Region addRegion(Region region);

    Region updateRegion(Region region);

    void deleteRegionById(UUID id);
}
