package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError;
import com.senla.worklog.reminder.employee.application.exception.ApplicationException;
import com.senla.worklog.reminder.employee.application.exception.UnexpectedApplicationException;
import com.senla.worklog.reminder.employee.application.exception.wrapper.ExceptionWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError.internalServerError;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * An exception handler for handling {@link UnexpectedApplicationException}, which is thrown when exception in
 * application service was not wrapped by specific {@link ExceptionWrapper} into corresponding {@link ApplicationException}.
 * This handler returns a HTTP 500 Internal Server Error
 */
@Slf4j
@Component
public class UnexpectedApplicationExceptionHandler extends AbstractRestAdapterExceptionHandler {

    /**
     * Handles the exception passed as parameter and returns a {@link ResponseEntity} containing an {@link ApiError}.
     * If the passed exception is an instance of {@link UnexpectedApplicationException}, it logs a warning
     * message and returns a {@link ResponseEntity} with HTTP status code 500 (Internal Server Error).
     * <p>
     * If the passed exception is not an instance of UnexpectedApplicationException, it delegates
     * the handling to the parent class method {@link #handleUnsupportedExceptionType(Exception)} handleUnsupportedExceptionType()}
     *
     * @param e the exception to handle
     * @return a {@link ResponseEntity} containing an {@link ApiError} with a HTTP 500 status code
     */
    @Override
    public ResponseEntity<ApiError> handleException(Exception e) {
        if (e instanceof UnexpectedApplicationException) {
            log.warn("Resolved UnexpectedApplicationException: {}. " +
                    "Consider exception hierarchy and exception handling in " +
                    "domain and application layers to avoid this error", e.getMessage(), e);
            return new ResponseEntity<>(internalServerError(), INTERNAL_SERVER_ERROR);
        }
        return handleUnsupportedExceptionType(e);
    }

    /**
     * Returns the type of exception that this handler can handle, which is
     * {@link UnexpectedApplicationException}.
     *
     * @return the {@link UnexpectedApplicationException} class
     */
    @Override
    public Class<? extends Throwable> getExceptionType() {
        return UnexpectedApplicationException.class;
    }
}
