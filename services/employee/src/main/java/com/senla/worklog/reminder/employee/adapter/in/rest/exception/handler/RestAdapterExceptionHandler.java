package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError;
import org.springframework.http.ResponseEntity;

/**
 * Interface for handling exceptions thrown by REST API adapters
 */
public interface RestAdapterExceptionHandler {

    /**
     * Handle an exception thrown by a REST API adapter
     *
     * @param e the exception to handle
     * @return a ResponseEntity containing an ApiError object with details about the exception
     */
    ResponseEntity<ApiError> handleException(Exception e);

    /**
     * Get the type of exception that this handler can handle
     *
     * @return the type of exception that this handler can handle
     */
    Class<? extends Throwable> getExceptionType();
}
