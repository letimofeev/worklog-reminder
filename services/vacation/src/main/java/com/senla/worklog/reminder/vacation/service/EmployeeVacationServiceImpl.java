package com.senla.worklog.reminder.vacation.service;

import com.senla.worklog.reminder.vacation.exception.CalendarVacationException;
import com.senla.worklog.reminder.vacation.model.EmployeeVacation;
import com.senla.worklog.reminder.vacation.repository.EmployeeVacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeVacationServiceImpl implements EmployeeVacationService {
    private final EmployeeVacationRepository vacationRepository;
    private final CalendarVacationService calendarVacationService;

    @Override
    public List<EmployeeVacation> getEmployeeVacations(Long employeeId, LocalDate dateFrom, LocalDate dateTo) {
        return vacationRepository.findAllByEmployeeIdAndDateBetween(employeeId, dateFrom, dateTo);
    }

    @Override
    public EmployeeVacation addEmployeeVacation(EmployeeVacation employeeVacation) {
        var date = employeeVacation.getDate();
        if (calendarVacationService.isCalendarVacation(date)) {
            throw new CalendarVacationException("Date '" + date + "' is calendar vacation");
        }
        return vacationRepository.save(employeeVacation);
    }

    @Override
    public EmployeeVacation updateEmployeeVacation(EmployeeVacation employeeVacation) {
        return vacationRepository.save(employeeVacation);
    }

    @Override
    public void deleteEmployeeVacationById(Long id) {
        vacationRepository.deleteById(id);
    }
}
