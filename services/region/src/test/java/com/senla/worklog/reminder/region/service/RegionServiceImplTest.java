package com.senla.worklog.reminder.region.service;

import com.senla.worklog.reminder.region.broker.RegionBroker;
import com.senla.worklog.reminder.region.event.RegionDeletedEventData;
import com.senla.worklog.reminder.region.exception.DuplicateRegionException;
import com.senla.worklog.reminder.region.exception.RegionNotFoundException;
import com.senla.worklog.reminder.region.model.Region;
import com.senla.worklog.reminder.region.repository.RegionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.senla.worklog.reminder.region.event.RegionEventType.REGION_CREATED;
import static com.senla.worklog.reminder.region.event.RegionEventType.REGION_DELETED;
import static com.senla.worklog.reminder.region.event.RegionEventType.REGION_UPDATED;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegionServiceImplTest {
    @Mock
    private RegionRepository regionRepository;

    @Mock
    private RegionBroker regionBroker;

    @InjectMocks
    private RegionServiceImpl regionService;

    @Test
    public void getAllRegions_shouldReturnAllRegions() {
        var region1 = new Region(randomUUID(), "Region 1");
        var region2 = new Region(randomUUID(), "Region 2");
        var expectedRegions = List.of(region1, region2);

        when(regionRepository.findAll()).thenReturn(expectedRegions);

        var actualRegions = regionService.getAllRegions();

        assertEquals(expectedRegions, actualRegions);
    }

    @Test
    public void getRegionById_shouldReturnRegion_whenExists() {
        var id = randomUUID();
        var expectedRegion = new Region(id, "Region");

        when(regionRepository.findById(id)).thenReturn(Optional.of(expectedRegion));

        var actualRegion = regionService.getRegionById(id);

        assertEquals(expectedRegion, actualRegion);
    }

    @Test
    public void getRegionById_shouldThrowRegionNotFoundException_whenNotExists() {
        var id = randomUUID();

        when(regionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RegionNotFoundException.class, () -> regionService.getRegionById(id));
    }

    @Test
    public void addRegion_shouldCreateRegion_whenNotExists() {
        var region = new Region();
        region.setName("Region");

        var createdRegion = new Region(randomUUID(), region.getName());

        when(regionRepository.existsByName(region.getName())).thenReturn(false);
        when(regionRepository.save(region)).thenReturn(createdRegion);

        regionService.addRegion(region);

        verify(regionRepository).save(region);
        verify(regionBroker).sendRegionEvent(argThat(regionEvent ->
                regionEvent.getEventType() == REGION_CREATED &&
                        regionEvent.getData().equals(createdRegion)));
    }

    @Test
    public void addRegion_shouldThrowDuplicateRegionException_whenExists() {
        var region = new Region();
        region.setName("Region");

        when(regionRepository.existsByName(region.getName())).thenReturn(true);

        assertThrows(DuplicateRegionException.class, () -> regionService.addRegion(region));
    }

    @Test
    public void updateRegion_shouldUpdateRegion_whenExists() {
        var id = randomUUID();
        var region = new Region(id, "Region");

        when(regionRepository.existsById(id)).thenReturn(true);
        when(regionRepository.existsByNameAndIdIsNot(region.getName(), id)).thenReturn(false);

        regionService.updateRegion(region);

        verify(regionRepository).save(region);
        verify(regionBroker).sendRegionEvent(argThat(regionEvent ->
                regionEvent.getEventType() == REGION_UPDATED &&
                        regionEvent.getData().equals(region)));
    }

    @Test
    public void updateRegion_shouldThrowRegionNotFoundException_whenNotExists() {
        var id = randomUUID();
        var region = new Region(id, "Region");

        when(regionRepository.existsById(id)).thenReturn(false);

        assertThrows(RegionNotFoundException.class, () -> regionService.updateRegion(region));
    }

    @Test
    public void updateRegion_shouldThrowDuplicateRegionException_whenDuplicateExists() {
        var id = randomUUID();

        var region = new Region(id, "Region");

        when(regionRepository.existsById(id)).thenReturn(true);
        when(regionRepository.existsByNameAndIdIsNot(region.getName(), id)).thenReturn(true);

        assertThrows(DuplicateRegionException.class, () -> regionService.updateRegion(region));
    }

    @Test
    public void deleteRegionById_shouldDeleteRegion_whenExists() {
        var id = randomUUID();

        when(regionRepository.existsById(id)).thenReturn(true);

        regionService.deleteRegionById(id);

        verify(regionRepository).deleteById(id);
        verify(regionBroker).sendRegionEvent(argThat(regionEvent ->
                regionEvent.getEventType() == REGION_DELETED &&
                        ((RegionDeletedEventData) regionEvent.getData()).getId().equals(id)));
    }

    @Test
    public void deleteRegionById_shouldThrowRegionNotFoundException_whenNotExists() {
        var id = randomUUID();

        when(regionRepository.existsById(id)).thenReturn(false);

        assertThrows(RegionNotFoundException.class, () -> regionService.deleteRegionById(id));
    }
}