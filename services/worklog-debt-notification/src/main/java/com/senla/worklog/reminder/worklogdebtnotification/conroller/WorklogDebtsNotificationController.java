package com.senla.worklog.reminder.worklogdebtnotification.conroller;

import com.senla.worklog.reminder.worklogdebtnotification.service.WorklogDebtsNotificationService;
import com.senla.worklog.reminder.worklogdebtnotification.model.EmployeeWorklogDebts;
import com.senla.worklog.reminder.worklogdebtnotification.model.NotificationResponse;
import com.senla.worklog.reminder.worklogdebtnotification.validator.DateRangeRequestParameters;
import com.senla.worklog.reminder.worklogdebtnotification.validator.ValidationSequence;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.List;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.LocalDate.now;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@Validated
@CrossOrigin
@RestController
@RequestMapping("/api/worklog-debts-notifications")
@RequiredArgsConstructor
public class WorklogDebtsNotificationController {
    private final WorklogDebtsNotificationService notificationService;

    @PostMapping(produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<NotificationResponse> sendNotifications() {
        var dateFrom = now().with(MONDAY);
        var dateTo = now().with(FRIDAY);
        return notificationService.sendDebtsNotifications(dateFrom, dateTo);
    }

    @PostMapping(params = {"dateFrom"}, produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<NotificationResponse> sendNotifications(@PastOrPresent(message = "{dateFrom.pastOrPresent}")
                                                        @RequestParam
                                                        @DateTimeFormat(iso = DATE)
                                                        LocalDate dateFrom) {
        var dateTo = now().with(FRIDAY);
        return notificationService.sendDebtsNotifications(dateFrom, dateTo);
    }

    @PostMapping(params = {"dateFrom", "dateTo"}, produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<NotificationResponse> sendNotifications(@Validated(ValidationSequence.class) DateRangeRequestParameters parameters) {
        var dateFrom = parameters.getDateFrom();
        var dateTo = parameters.getDateTo();
        return notificationService.sendDebtsNotifications(dateFrom, dateTo);
    }

    @PostMapping(path = "/custom", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<NotificationResponse> sendNotifications(@RequestBody List<EmployeeWorklogDebts> worklogDebts) {
        return notificationService.sendDebtsNotifications(worklogDebts);
    }
}
