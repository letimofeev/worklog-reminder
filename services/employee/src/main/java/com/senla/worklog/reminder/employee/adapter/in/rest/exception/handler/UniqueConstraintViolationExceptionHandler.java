package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError;
import com.senla.worklog.reminder.employee.adapter.in.rest.dto.AttributeApiSubError;
import com.senla.worklog.reminder.employee.application.exception.UniqueConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError.badRequest;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * An exception handler for handling {@link UniqueConstraintViolationException}, which is thrown when the
 * application uniqueness rules violated. This handler returns a HTTP 400 Bad Request response with a user-friendly error message
 */
@Slf4j
@Component
public class UniqueConstraintViolationExceptionHandler extends AbstractRestAdapterExceptionHandler {

    /**
     * Handles the {@link UniqueConstraintViolationException} and returns a HTTP 400 Bad Request response
     * with a user-friendly error message.
     * <p>
     * If the passed exception is not an instance of ResourceNotFoundException, it delegates
     * the handling to the parent class method handleUnsupportedExceptionType().
     *
     * @param e the {@link UniqueConstraintViolationException} to handle
     * @return a {@link ResponseEntity} containing an {@link ApiError} with a HTTP 404 status code and a
     * user-friendly error message
     */
    @Override
    public ResponseEntity<ApiError> handleException(Exception e) {
        if (e instanceof UniqueConstraintViolationException) {
            log.debug("Resolved UniqueConstraintViolationException: {}", e.getMessage());

            var castedEx = (UniqueConstraintViolationException) e;
            var subErrors = List.of(new AttributeApiSubError()
                    .setAttributeName(castedEx.attributeName())
                    .setAttributeValue(castedEx.attributeValue())
                    .setMessage(castedEx.causeMessage()));
            var apiError = badRequest(castedEx.getMessage(), subErrors);

            return new ResponseEntity<>(apiError, BAD_REQUEST);
        }
        return handleUnsupportedExceptionType(e);
    }

    /**
     * Returns the type of exception that this handler can handle, which is
     * {@link UniqueConstraintViolationException}.
     *
     * @return the {@link UniqueConstraintViolationException} class
     */
    @Override
    public Class<? extends Throwable> getExceptionType() {
        return UniqueConstraintViolationException.class;
    }
}
