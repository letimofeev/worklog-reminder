package com.senla.worklog.reminder.vacation.repository;

import com.senla.worklog.reminder.vacation.model.CalendarVacation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface CalendarVacationRepository extends JpaRepository<CalendarVacation, LocalDate> {
    List<CalendarVacation> findAllByRegionIdAndDateBetween(UUID regionId, LocalDate dateFrom, LocalDate dateTo);
}
