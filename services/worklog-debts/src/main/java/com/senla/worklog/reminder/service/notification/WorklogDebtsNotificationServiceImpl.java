package com.senla.worklog.reminder.service.notification;

import com.senla.worklog.reminder.api.notification.model.NotificationResponse;
import com.senla.worklog.reminder.dto.mapper.WorklogDebtsDtoMapper;
import com.senla.worklog.reminder.service.worklogdebt.WorklogDebtsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class WorklogDebtsNotificationServiceImpl implements WorklogDebtsNotificationService {
    private final WorklogDebtsService worklogDebtsService;
    private final WorklogDebtsDtoMapper worklogDebtsDtoMapper;
    private final WorklogDebtsNotificationSender notificationSender;

    @Override
    public Flux<NotificationResponse> sendWorklogDebtNotifications(LocalDate dateFrom, LocalDate dateTo) {
        var worklogDebts = worklogDebtsService.getAllForPeriod(dateFrom, dateTo);
        var worklogDebtsDto = worklogDebtsDtoMapper.mapToDto(worklogDebts);
        return notificationSender.sendWorklogDebtNotifications(worklogDebtsDto);
    }
}
