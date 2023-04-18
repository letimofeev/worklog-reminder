package com.senla.worklog.reminder.vacation.repository;

import com.senla.worklog.reminder.vacation.model.EmployeeVacation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeVacationRepository extends JpaRepository<EmployeeVacation, Long> {
    List<EmployeeVacation> findAllByEmployeeIdAndDateBetween(Long employeeId, LocalDate dateFrom, LocalDate dateTo);
}
