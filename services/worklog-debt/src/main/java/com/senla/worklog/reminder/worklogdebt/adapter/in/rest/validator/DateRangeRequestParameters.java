package com.senla.worklog.reminder.worklogdebt.adapter.in.rest.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ParameterObject
@DateFromMoreRecentThanDateTo
public class DateRangeRequestParameters {
    @NotNull
    @PastOrPresent(message = "{dateFrom.pastOrPresent}")
    @DateTimeFormat(iso = DATE)
    private LocalDate dateFrom;

    @NotNull
    @DateTimeFormat(iso = DATE)
    private LocalDate dateTo;
}
