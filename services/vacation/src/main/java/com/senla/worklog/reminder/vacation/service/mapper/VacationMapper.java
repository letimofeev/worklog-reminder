package com.senla.worklog.reminder.vacation.service.mapper;

import com.senla.worklog.reminder.vacation.model.CalendarVacation;
import com.senla.worklog.reminder.vacation.model.EmployeeVacation;
import com.senla.worklog.reminder.vacation.model.Vacation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VacationMapper {
    Vacation mapToVacation(EmployeeVacation employeeVacation);

    @Mapping(target = "employeeId", source = "employeeId")
    Vacation mapToVacation(CalendarVacation calendarVacation, Long employeeId);
}
