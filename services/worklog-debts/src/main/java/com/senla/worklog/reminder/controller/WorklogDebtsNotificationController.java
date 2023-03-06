package com.senla.worklog.reminder.controller;

import com.senla.worklog.reminder.api.notification.model.NotificationResponse;
import com.senla.worklog.reminder.service.notification.WorklogDebtsNotificationService;
import com.senla.worklog.reminder.validator.DateRangeRequestParameters;
import com.senla.worklog.reminder.validator.ValidationSequence;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@Validated
@RestController
@RequestMapping("/worklog-debts/notifications")
@RequiredArgsConstructor
public class WorklogDebtsNotificationController {
    private final WorklogDebtsNotificationService notificationService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<NotificationResponse> sendNotifications() {
        return notificationService.sendWorklogDebtNotifications();
    }

    @GetMapping(params = {"dateFrom"}, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<NotificationResponse> sendNotifications(@PastOrPresent(message = "{dateFrom.pastOrPresent}")
                                                        @RequestParam
                                                        @DateTimeFormat(iso = DATE)
                                                        LocalDate dateFrom) {
        return notificationService.sendWorklogDebtNotifications(dateFrom);
    }

    @GetMapping(params = {"dateFrom", "dateTo"}, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<NotificationResponse> sendNotifications(@Validated(ValidationSequence.class) DateRangeRequestParameters parameters) {
        var dateFrom = parameters.getDateFrom();
        var dateTo = parameters.getDateTo();
        return notificationService.sendWorklogDebtNotifications(dateFrom, dateTo);
    }
}
