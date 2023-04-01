package com.senla.worklog.reminder.region.service;

import com.senla.worklog.reminder.region.broker.RegionBroker;
import com.senla.worklog.reminder.region.model.Region;
import com.senla.worklog.reminder.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

import static com.senla.worklog.reminder.region.event.RegionEvent.regionCreatedEvent;
import static com.senla.worklog.reminder.region.event.RegionEvent.regionDeletedEvent;
import static com.senla.worklog.reminder.region.event.RegionEvent.regionUpdatedEvent;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;
    private final RegionBroker regionBroker;

    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }

    public Region getRegionById(UUID id) {
        return regionRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Region addRegion(Region region) {
        var createdRegion = regionRepository.save(region);
        regionBroker.sendRegionEvent(regionCreatedEvent(createdRegion));
        return createdRegion;
    }

    public Region updateRegion(Region region) {
        if (!regionRepository.existsById(region.getId())) {
            throw new EntityNotFoundException();
        }
        var updatedRegion = regionRepository.save(region);
        regionBroker.sendRegionEvent(regionUpdatedEvent(region));
        return updatedRegion;
    }

    public void deleteRegionById(UUID id) {
        if (!regionRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        regionBroker.sendRegionEvent(regionDeletedEvent(id));
        regionRepository.deleteById(id);
    }
}
