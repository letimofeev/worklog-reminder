package com.senla.worklog.reminder.region.service;

import com.senla.worklog.reminder.region.model.Region;
import com.senla.worklog.reminder.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;

    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }

    public Region getRegionById(Long id) {
        return regionRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Region addRegion(Region region) {
        return regionRepository.save(region);
    }

    public Region updateRegion(Region region) {
        if (regionRepository.existsById(region.getId())) {
            throw new EntityNotFoundException();
        }
        return regionRepository.save(region);
    }

    public void deleteRegionById(Long id) {
        regionRepository.deleteById(id);
    }
}
