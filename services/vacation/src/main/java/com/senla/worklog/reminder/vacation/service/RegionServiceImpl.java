package com.senla.worklog.reminder.vacation.service;

import com.senla.worklog.reminder.vacation.model.Region;
import com.senla.worklog.reminder.vacation.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;

    @Override
    public Region addRegion(Region region) {
        log.debug("Saving region '{}'", region.getName());
        return regionRepository.save(region);
    }

    @Override
    public Region updateRegion(Region region) {
        log.debug("Updating region with id '{}'", region.getId());
        return regionRepository.save(region);
    }

    @Override
    public void deleteRegionById(UUID id) {
        log.debug("Deleting region with id '{}'", id);
        if (regionRepository.existsById(id)) {
            regionRepository.deleteById(id);
        }
    }
}
