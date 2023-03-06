package com.senla.worklog.reminder.controller;

import com.senla.worklog.reminder.api.notification.model.NotificationResponse;
import com.senla.worklog.reminder.dto.WorklogDebtsDto;
import com.senla.worklog.reminder.service.notification.WorklogDebtsNotificationService;
import com.senla.worklog.reminder.validator.DateRangeRequestParameters;
import com.senla.worklog.reminder.validator.ValidationSequence;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@Validated
@RestController
@RequestMapping("/worklog-debts/notifications")
@RequiredArgsConstructor
public class WorklogDebtsNotificationController {
    private final WorklogDebtsNotificationService notificationService;

    @PostMapping(produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<NotificationResponse> sendNotifications() {
        return notificationService.sendWorklogDebtNotifications();
    }

    @PostMapping(params = {"dateFrom"}, produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<NotificationResponse> sendNotifications(@PastOrPresent(message = "{dateFrom.pastOrPresent}")
                                                        @RequestParam
                                                        @DateTimeFormat(iso = DATE)
                                                        LocalDate dateFrom) {
        return notificationService.sendWorklogDebtNotifications(dateFrom);
    }

    @PostMapping(params = {"dateFrom", "dateTo"}, produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<NotificationResponse> sendNotifications(@Validated(ValidationSequence.class) DateRangeRequestParameters parameters) {
        var dateFrom = parameters.getDateFrom();
        var dateTo = parameters.getDateTo();
        return notificationService.sendWorklogDebtNotifications(dateFrom, dateTo);
    }

    @PostMapping(produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<NotificationResponse> sendNotifications(@RequestBody WorklogDebtsDto worklogDebtsDto) {
        return notificationService.sendWorklogDebtNotifications(worklogDebtsDto);
    }
}
