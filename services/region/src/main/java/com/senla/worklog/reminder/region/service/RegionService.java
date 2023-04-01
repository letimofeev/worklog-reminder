package com.senla.worklog.reminder.region.service;

import com.senla.worklog.reminder.region.model.Region;

import java.util.List;

public interface RegionService {
    List<Region> getAllRegions();

    Region getRegionById(Long id);

    Region addRegion(Region region);

    Region updateRegion(Region region);

    void deleteRegionById(Long id);
}
