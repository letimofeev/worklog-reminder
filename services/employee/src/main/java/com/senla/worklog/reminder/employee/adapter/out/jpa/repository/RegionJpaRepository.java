package com.senla.worklog.reminder.employee.adapter.out.jpa.repository;

import com.senla.worklog.reminder.employee.adapter.out.jpa.entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RegionJpaRepository extends JpaRepository<RegionEntity, UUID> {
}
