package com.senla.worklog.reminder.dto.mapper;

import com.senla.worklog.reminder.api.notification.model.NotificationUser;
import com.senla.worklog.reminder.dto.EmployeeDetailsDto;
import com.senla.worklog.reminder.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeDetailsDtoMapperImpl implements EmployeeDetailsDtoMapper {
    private final ModelMapper mapper;

    @Override
    public EmployeeDetailsDto mapToDetails(EmployeeDto employeeDto, NotificationUser user) {
        var botConnected = false;
        var notificationEnabled = false;
        if (user.getLogin().equals(employeeDto.getSkypeLogin())) {
            botConnected = true;
            notificationEnabled = user.getEnabled();
        }
        return mapToDetails(employeeDto)
                .setBotConnected(botConnected)
                .setNotificationEnabled(notificationEnabled);
    }

    @Override
    public EmployeeDetailsDto mapToDetails(EmployeeDto employeeDto) {
        return mapper.map(employeeDto, EmployeeDetailsDto.class)
                .setBotConnected(false)
                .setNotificationEnabled(false);
    }
}
