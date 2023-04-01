package com.senla.worklog.reminder.region.service;

import com.senla.worklog.reminder.region.broker.RegionBroker;
import com.senla.worklog.reminder.region.exception.RegionNotFoundException;
import com.senla.worklog.reminder.region.model.Region;
import com.senla.worklog.reminder.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static com.senla.worklog.reminder.region.event.RegionEvent.regionCreatedEvent;
import static com.senla.worklog.reminder.region.event.RegionEvent.regionDeletedEvent;
import static com.senla.worklog.reminder.region.event.RegionEvent.regionUpdatedEvent;

@Service
@Transactional
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;
    private final RegionBroker regionBroker;

    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }

    public Region getRegionById(UUID id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> new RegionNotFoundException(id));
    }

    public Region addRegion(Region region) {
        var createdRegion = regionRepository.save(region);
        regionBroker.sendRegionEvent(regionCreatedEvent(createdRegion));
        return createdRegion;
    }

    public Region updateRegion(Region region) {
        var id = region.getId();
        if (!regionRepository.existsById(id)) {
            throw new RegionNotFoundException(id);
        }
        var updatedRegion = regionRepository.save(region);
        regionBroker.sendRegionEvent(regionUpdatedEvent(region));
        return updatedRegion;
    }

    public void deleteRegionById(UUID id) {
        if (!regionRepository.existsById(id)) {
            throw new RegionNotFoundException(id);
        }
        regionBroker.sendRegionEvent(regionDeletedEvent(id));
        regionRepository.deleteById(id);
    }
}
