package com.senla.worklog.reminder.vacation.exception.handler;

import com.senla.worklog.reminder.exception.handler.model.ApiError;
import com.senla.worklog.reminder.vacation.exception.CalendarVacationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.senla.worklog.reminder.exception.handler.model.ApiError.badRequest;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CalendarVacationException.class)
    public ResponseEntity<ApiError> handleEmployeeVacationException(CalendarVacationException e) {
        log.trace("Resolved NonWorkingDayVacationException: {}", e.getMessage());
        var apiError = badRequest(e.getMessage());
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }
}
