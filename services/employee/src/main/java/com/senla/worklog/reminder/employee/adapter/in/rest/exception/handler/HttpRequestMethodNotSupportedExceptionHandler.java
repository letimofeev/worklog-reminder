package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import static com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError.methodNotAllowed;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

/**
 * An exception handler for handling {@link HttpRequestMethodNotSupportedException}, which is thrown when a
 * request handler does not support a specific request method. This handler returns a HTTP 405 Method Not Allowed
 * response with a user-friendly error message
 */
@Slf4j
@Component
public class HttpRequestMethodNotSupportedExceptionHandler extends AbstractRestAdapterExceptionHandler {

    /**
     * Handles the {@link HttpRequestMethodNotSupportedException} and returns a HTTP 405 Method Not Allowed response
     * with a user-friendly error message
     * <p>
     * If the passed exception is not an instance of HttpRequestMethodNotSupportedException, it delegates
     * the handling to the parent class method {@link #handleUnsupportedExceptionType(Exception)} handleUnsupportedExceptionType()}
     *
     * @param e the {@link HttpRequestMethodNotSupportedException} to handle
     * @return a {@link ResponseEntity} containing an {@link ApiError} with a HTTP 405 status code and
     * a user-friendly error message
     */
    @Override
    public ResponseEntity<ApiError> handleException(Exception e) {
        if (e instanceof HttpRequestMethodNotSupportedException) {
            log.trace("Resolved HttpRequestMethodNotSupportedException: {}", e.getMessage());
            var message = e.getMessage();
            var apiError = methodNotAllowed(message);
            return new ResponseEntity<>(apiError, METHOD_NOT_ALLOWED);
        }
        return handleUnsupportedExceptionType(e);
    }

    /**
     * Returns the type of exception that this handler can handle, which is {@link HttpRequestMethodNotSupportedException}.
     *
     * @return the {@link HttpRequestMethodNotSupportedException} class
     */
    @Override
    public Class<? extends Throwable> getExceptionType() {
        return HttpRequestMethodNotSupportedException.class;
    }
}
