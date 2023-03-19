package com.senla.worklog.reminder.employee.adapter.out.jpa.repository;

import com.senla.worklog.reminder.employee.adapter.out.jpa.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeJpaRepository extends JpaRepository<EmployeeEntity, Long> {
    Optional<EmployeeEntity> findByJiraKey(String jiraKey);
}
