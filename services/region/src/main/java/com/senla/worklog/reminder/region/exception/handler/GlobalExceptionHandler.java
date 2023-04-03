package com.senla.worklog.reminder.region.exception.handler;

import com.senla.worklog.reminder.exception.handler.model.ApiError;
import com.senla.worklog.reminder.region.exception.DuplicateRegionException;
import com.senla.worklog.reminder.region.exception.RegionNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.senla.worklog.reminder.exception.handler.model.ApiError.badRequest;
import static com.senla.worklog.reminder.exception.handler.model.ApiError.notFound;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RegionNotFoundException.class)
    public ResponseEntity<ApiError> handleRegionNotFound(RegionNotFoundException e) {
        var apiError = notFound(e.getMessage());
        return new ResponseEntity<>(apiError, NOT_FOUND);
    }

    @ExceptionHandler(DuplicateRegionException.class)
    public ResponseEntity<ApiError> handleDuplicateRegion(DuplicateRegionException e) {
        var apiError = badRequest(e.getMessage());
        return new ResponseEntity<>(apiError, NOT_FOUND);
    }
}
