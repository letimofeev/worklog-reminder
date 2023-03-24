package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError.badRequest;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * An exception handler for handling {@link MethodArgumentTypeMismatchException}, which is thrown when the type of a request parameter
 * does not match the expected type. This handler returns a HTTP 400 Bad Request response with a user-friendly error message
 * and a validation error
 */
@Slf4j
@Component
public class MethodArgumentTypeMismatchExceptionHandler extends AbstractRestAdapterExceptionHandler {

    /**
     * Handles the {@link MethodArgumentTypeMismatchException} and returns a HTTP 400 Bad Request response with a user-friendly error
     * message and a validation error
     * <p>
     * If the passed exception is not an instance of MethodArgumentTypeMismatchException, it delegates
     * the handling to the parent class method {@link #handleUnsupportedExceptionType(Exception)} handleUnsupportedExceptionType()}
     *
     * @param e the {@link MethodArgumentTypeMismatchException} to handle
     * @return a {@link ResponseEntity} containing an {@link ApiError} with a HTTP 400 status code, a
     * user-friendly error message, and a validation error
     */
    @Override
    public ResponseEntity<ApiError> handleException(Exception e) {
        if (e instanceof MethodArgumentTypeMismatchException) {
            var castedEx = (MethodArgumentTypeMismatchException) e;
            log.trace("Resolved MethodArgumentTypeMismatchException: {}", castedEx.getMessage());
            var message = "Failed to parse request parameter with name " + castedEx.getName() +
                    " and value " + castedEx.getValue();
            var apiError = badRequest(message);
            return new ResponseEntity<>(apiError, BAD_REQUEST);
        }
        return handleUnsupportedExceptionType(e);
    }

    /**
     * Returns the type of exception that this handler can handle, which is
     * {@link MethodArgumentTypeMismatchException}.
     *
     * @return the {@link MethodArgumentTypeMismatchException} class
     */
    @Override
    public Class<? extends Throwable> getExceptionType() {
        return MethodArgumentTypeMismatchException.class;
    }
}
