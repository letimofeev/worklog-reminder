package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;

import static com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError.badRequest;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * An exception handler for handling {@link HttpMessageNotReadableException}, which is thrown when there is an error
 * while reading an HTTP message, such as an invalid JSON payload. This handler returns a HTTP 400 Bad Request
 * response with a user-friendly error message.
 */
@Slf4j
@Component
public class HttpMessageNotReadableExceptionHandler extends AbstractRestAdapterExceptionHandler {

    /**
     * Handles the {@link HttpMessageNotReadableException} and returns a HTTP 400 Bad Request response with a user-friendly
     * error message
     * <p>
     * If the passed exception is not an instance of HttpMessageNotReadableException, it delegates
     * the handling to the parent class method {@link #handleUnsupportedExceptionType(Exception)} handleUnsupportedExceptionType()}
     *
     * @param e the {@link HttpMessageNotReadableException} to handle
     * @return a {@link ResponseEntity} containing an {@link ApiError} with a HTTP 400 status code and
     * a user-friendly error message
     */
    @Override
    public ResponseEntity<ApiError> handleException(Exception e) {
        if (e instanceof HttpMessageNotReadableException) {
            var castedEx = (HttpMessageNotReadableException) e;
            log.trace("Resolved HttpMessageNotReadableExceptionHandler: {}", castedEx.getMessage());
            var message = "Failed to parse request body";
            var apiError = badRequest(message);
            return new ResponseEntity<>(apiError, BAD_REQUEST);
        }
        return handleUnsupportedExceptionType(e);
    }

    /**
     * Returns the type of exception that this handler can handle, which is {@link HttpMessageNotReadableException}.
     *
     * @return the {@link HttpMessageNotReadableException} class
     */
    @Override
    public Class<? extends Throwable> getExceptionType() {
        return HttpMessageNotReadableException.class;
    }
}
