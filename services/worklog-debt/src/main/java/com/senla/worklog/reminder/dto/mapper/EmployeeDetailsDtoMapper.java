package com.senla.worklog.reminder.dto.mapper;

import com.senla.worklog.reminder.api.notification.model.NotificationUser;
import com.senla.worklog.reminder.dto.EmployeeDetailsDto;
import com.senla.worklog.reminder.dto.EmployeeDto;

public interface EmployeeDetailsDtoMapper {
    EmployeeDetailsDto mapToDetails(EmployeeDto employeeDto, NotificationUser user);

    EmployeeDetailsDto mapToDetails(EmployeeDto employeeDto);
}
