package com.senla.worklog.reminder.region.repository;

import com.senla.worklog.reminder.region.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RegionRepository extends JpaRepository<Region, UUID> {
}
