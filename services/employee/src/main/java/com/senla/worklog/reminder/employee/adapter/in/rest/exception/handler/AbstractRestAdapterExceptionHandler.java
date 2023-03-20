package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import static com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError.internalServerError;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
public abstract class AbstractRestAdapterExceptionHandler implements RestAdapterExceptionHandler {
    protected ResponseEntity<ApiError> handleUnsupportedExceptionType(Exception e) {
        log.warn("Unsupported exception was passed to ResourceNotFoundExceptionHandler: {}",
                e.getClass().getSimpleName(), e);
        return new ResponseEntity<>(internalServerError(), INTERNAL_SERVER_ERROR);
    }
}
