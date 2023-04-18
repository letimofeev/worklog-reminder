package com.senla.worklog.reminder.vacation.repository;

import com.senla.worklog.reminder.vacation.model.EmployeeVacation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeVacationRepository extends JpaRepository<EmployeeVacation, Long> {
    List<EmployeeVacation> findAllByEmployeeId(Long employeeId);
}
