package com.senla.worklog.reminder.service.notification;

import com.senla.worklog.reminder.api.notification.client.NotificationClient;
import com.senla.worklog.reminder.api.notification.model.NotificationResponse;
import com.senla.worklog.reminder.dto.EmployeeWorklogDebtsDto;
import com.senla.worklog.reminder.service.worklogdebt.EmployeeWorklogDebtsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorklogDebtsNotificationServiceImpl implements WorklogDebtsNotificationService {
    private final EmployeeWorklogDebtsService worklogDebtsService;
    private final NotificationClient notificationClient;
    private final NotificationBuilder notificationBuilder;

    @Override
    public Flux<NotificationResponse> sendWorklogDebtNotifications(LocalDate dateFrom, LocalDate dateTo) {
        var worklogDebts = worklogDebtsService.getEmployeesDebts(dateFrom, dateTo);
        return sendWorklogDebtNotifications(worklogDebts);
    }

    @Override
    public Flux<NotificationResponse> sendWorklogDebtNotifications(List<EmployeeWorklogDebtsDto> worklogDebtsDto) {
        var notifications = worklogDebtsDto.stream()
                .map(notificationBuilder::buildNotification)
                .collect(Collectors.toList());
        return notificationClient.sendNotifications(notifications);
    }
}
