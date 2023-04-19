package com.senla.worklog.reminder.vacation.repository;

import com.senla.worklog.reminder.vacation.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RegionRepository extends JpaRepository<Region, UUID> {
}
