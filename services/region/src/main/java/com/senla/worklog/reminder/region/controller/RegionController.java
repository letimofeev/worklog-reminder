package com.senla.worklog.reminder.region.controller;

import com.senla.worklog.reminder.region.model.Region;
import com.senla.worklog.reminder.region.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class RegionController {
    private final RegionService regionService;

    @GetMapping
    public List<Region> getAllRegions() {
        return regionService.getAllRegions();
    }

    @GetMapping("/{id}")
    public Region getRegionById(@PathVariable UUID id) {
        return regionService.getRegionById(id);
    }

    @PostMapping
    public Region addRegion(@Valid @RequestBody Region region) {
        return regionService.addRegion(region);
    }

    @PutMapping
    public Region updateRegion(@Valid @RequestBody Region region) {
        return regionService.updateRegion(region);
    }

    @DeleteMapping("/{id}")
    public void deleteRegionById(@PathVariable UUID id) {
        regionService.deleteRegionById(id);
    }
}
