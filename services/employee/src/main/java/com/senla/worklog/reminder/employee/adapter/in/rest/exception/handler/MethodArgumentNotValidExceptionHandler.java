package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError;
import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiSubError;
import com.senla.worklog.reminder.employee.adapter.in.rest.dto.AttributeApiSubError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError.badRequest;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * An exception handler for handling {@link MethodArgumentNotValidException}, which is thrown when there is a validation error
 * while binding the request body to a Java object. This handler returns a HTTP 400 Bad Request response with a user-friendly
 * error message and a list of validation errors.
 */
@Slf4j
@Component
public class MethodArgumentNotValidExceptionHandler extends AbstractRestAdapterExceptionHandler {

    /**
     * Handles the {@link MethodArgumentNotValidException} and returns a HTTP 400 Bad Request response with a user-friendly error
     * message and a list of validation errors
     * <p>
     * If the passed exception is not an instance of MethodArgumentNotValidException, it delegates
     * the handling to the parent class method {@link #handleUnsupportedExceptionType(Exception)} handleUnsupportedExceptionType()}
     *
     * @param e the {@link MethodArgumentNotValidException} to handle
     * @return a {@link ResponseEntity} containing an {@link ApiError} with a HTTP 400 status code, a user-friendly error message,
     * and a list of validation errors
     */
    @Override
    public ResponseEntity<ApiError> handleException(Exception e) {
        if (e instanceof MethodArgumentNotValidException) {
            var ex = (MethodArgumentNotValidException) e;
            log.debug("Resolved BindException: {}", ex.getMessage());
            var message = "Validation failed";
            List<ApiSubError> subErrors = ex.getFieldErrors().stream()
                    .map(error -> new AttributeApiSubError(error.getDefaultMessage(),
                            error.getField(), String.valueOf(error.getRejectedValue())))
                    .collect(toList());
            var apiError = badRequest(message, subErrors);
            return new ResponseEntity<>(apiError, BAD_REQUEST);
        }
        return handleUnsupportedExceptionType(e);
    }

    /**
     * Returns the type of exception that this handler can handle, which is {@link MethodArgumentNotValidException}.
     *
     * @return the {@link MethodArgumentNotValidException} class
     */
    @Override
    public Class<? extends Throwable> getExceptionType() {
        return MethodArgumentNotValidException.class;
    }
}
