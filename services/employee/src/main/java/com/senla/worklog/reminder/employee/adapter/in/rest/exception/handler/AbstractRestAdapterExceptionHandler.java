package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import static com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError.internalServerError;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * An abstract base class for implementing {@link RestAdapterExceptionHandler} instances.
 * Provides default implementation for handling unsupported exception types
 */
@Slf4j
public abstract class AbstractRestAdapterExceptionHandler implements RestAdapterExceptionHandler {

    /**
     * Handle an exception of an unsupported type. This implementation logs a warning message and
     * returns a ResponseEntity with an internal server error status
     *
     * @param e the exception to handle
     * @return a ResponseEntity containing an ApiError object with details about the exception
     */
    protected ResponseEntity<ApiError> handleUnsupportedExceptionType(Exception e) {
        log.warn("Unsupported exception was passed to {}: {}",
                getClass().getSimpleName(), e.getClass().getSimpleName(), e);
        return new ResponseEntity<>(internalServerError(), INTERNAL_SERVER_ERROR);
    }
}
