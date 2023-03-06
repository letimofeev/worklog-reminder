package com.senla.worklog.reminder.service.notification;

import com.senla.worklog.reminder.api.notification.client.NotificationClient;
import com.senla.worklog.reminder.api.notification.model.NotificationResponse;
import com.senla.worklog.reminder.dto.mapper.WorklogDebtsDtoMapper;
import com.senla.worklog.reminder.service.worklogdebt.WorklogDebtsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorklogDebtsNotificationServiceImpl implements WorklogDebtsNotificationService {
    private final WorklogDebtsService worklogDebtsService;
    private final WorklogDebtsDtoMapper worklogDebtsDtoMapper;
    private final NotificationClient notificationClient;
    private final NotificationBuilder notificationBuilder;

    @Override
    public Flux<NotificationResponse> sendWorklogDebtNotifications(LocalDate dateFrom, LocalDate dateTo) {
        var worklogDebts = worklogDebtsService.getAllForPeriod(dateFrom, dateTo);
        var notifications = worklogDebtsDtoMapper.mapToDto(worklogDebts).getWorklogDebts()
                .stream()
                .map(notificationBuilder::buildNotification)
                .collect(Collectors.toList());
        return notificationClient.sendNotifications(notifications);
    }
}
