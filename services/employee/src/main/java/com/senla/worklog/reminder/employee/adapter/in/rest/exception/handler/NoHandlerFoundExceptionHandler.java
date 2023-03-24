package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.NoHandlerFoundException;

import static com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError.notFound;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * An exception handler for handling {@link NoHandlerFoundException}, which is thrown when mapping not found
 * for request. This handler returns a HTTP 404 Not Found response with a user-friendly error message
 */
@Slf4j
@Component
public class NoHandlerFoundExceptionHandler extends AbstractRestAdapterExceptionHandler {

    /**
     * Handles the {@link NoHandlerFoundException} and returns a HTTP 404 Not Found response
     * with a user-friendly error message
     * <p>
     * If the passed exception is not an instance of NoHandlerFoundException, it delegates
     * the handling to the parent class method {@link #handleUnsupportedExceptionType(Exception)} handleUnsupportedExceptionType()}
     *
     * @param e the {@link NoHandlerFoundException} to handle
     * @return a {@link ResponseEntity} containing an {@link ApiError} with a HTTP 404 status code and
     * a user-friendly error message
     */
    @Override
    public ResponseEntity<ApiError> handleException(Exception e) {
        if (e instanceof NoHandlerFoundException) {
            log.trace("Resolved NoHandlerFoundException: {}", e.getMessage());
            var message = e.getMessage();
            var apiError = notFound(message);
            return new ResponseEntity<>(apiError, NOT_FOUND);
        }
        return handleUnsupportedExceptionType(e);
    }

    /**
     * Returns the type of exception that this handler can handle, which is {@link NoHandlerFoundException}.
     *
     * @return the {@link NoHandlerFoundException} class
     */
    @Override
    public Class<? extends Throwable> getExceptionType() {
        return NoHandlerFoundException.class;
    }
}
