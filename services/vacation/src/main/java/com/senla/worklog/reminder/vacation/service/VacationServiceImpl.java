package com.senla.worklog.reminder.vacation.service;

import com.senla.worklog.reminder.vacation.model.CalendarVacation;
import com.senla.worklog.reminder.vacation.model.EmployeeVacation;
import com.senla.worklog.reminder.vacation.model.Vacation;
import com.senla.worklog.reminder.vacation.service.mapper.VacationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class VacationServiceImpl implements VacationService {
    private final EmployeeVacationService employeeVacationService;
    private final CalendarVacationService calendarVacationService;
    private final VacationMapper vacationMapper;

    @Override
    public List<Vacation> getVacations(UUID regionId, LocalDate dateFrom, LocalDate dateTo, Long employeeId) {
        var employeeVacations = employeeVacationService.getEmployeeVacations(employeeId, dateFrom, dateTo);
        var calendarVacations = calendarVacationService.getCalendarVacations(regionId, dateFrom, dateTo);
        return mergeVacations(employeeVacations, calendarVacations);
    }

    private List<Vacation> mergeVacations(List<EmployeeVacation> employeeVacations,
                                          List<CalendarVacation> calendarVacations) {
        var calendarVacationStream = calendarVacations.stream().map(vacationMapper::mapToVacation);
        var employeeVacationStream = employeeVacations.stream().map(vacationMapper::mapToVacation);
        return Stream.concat(calendarVacationStream, employeeVacationStream)
                .sorted(comparing(Vacation::getDate))
                .collect(toList());
    }
}
