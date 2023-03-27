package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import com.senla.common.exception.handler.model.ApiError;
import com.senla.common.exception.handler.model.AttributeApiSubError;
import com.senla.worklog.reminder.employee.application.exception.ResourceNotFoundException;
import com.senla.worklog.reminder.employee.application.exception.UnexpectedApplicationException;
import com.senla.worklog.reminder.employee.application.exception.UniqueConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

import static com.senla.common.exception.handler.model.ApiError.badRequest;
import static com.senla.common.exception.handler.model.ApiError.internalServerError;
import static com.senla.common.exception.handler.model.ApiError.notFound;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;


/**
 * Controller advice class that handles exceptions thrown in the REST adapter layer
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class RestAdapterExceptionHandler {

    /**
     * Handles the {@link ResourceNotFoundException} and returns a HTTP 404 Not Found response
     * with a user-friendly error message.
     *
     * @param e the {@link ResourceNotFoundException} to handle
     * @return a {@link ResponseEntity} containing an {@link ApiError} with a HTTP 404 status code and a
     * user-friendly error message
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException e) {
        log.debug("Resolved ResourceNotFoundException: {}", e.getMessage());

        var subErrors = List.of(new AttributeApiSubError()
                .setAttributeName(e.attributeName())
                .setAttributeValue(e.attributeValue())
                .setMessage(e.causeMessage()));
        var apiError = notFound(e.getMessage(), subErrors);

        return new ResponseEntity<>(apiError, NOT_FOUND);
    }

    /**
     * Handles the {@link UniqueConstraintViolationException} and returns a HTTP 400 Bad Request response
     * with a user-friendly error message
     *
     * @param e the {@link UniqueConstraintViolationException} to handle
     * @return a {@link ResponseEntity} containing an {@link ApiError} with a HTTP 404 status code and a
     * user-friendly error message
     */
    @ExceptionHandler(UniqueConstraintViolationException.class)
    public ResponseEntity<ApiError> handleUniqueConstraintViolationException(UniqueConstraintViolationException e) {
        log.debug("Resolved UniqueConstraintViolationException: {}", e.getMessage());

        var subErrors = List.of(new AttributeApiSubError()
                .setAttributeName(e.attributeName())
                .setAttributeValue(e.attributeValue())
                .setMessage(e.causeMessage()));
        var apiError = badRequest(e.getMessage(), subErrors);

        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    /**
     * Handles the exception passed as parameter and returns a {@link ResponseEntity} containing an {@link ApiError}.
     * If the passed exception is an instance of {@link UnexpectedApplicationException}, it logs a warning
     * message and returns a {@link ResponseEntity} with HTTP status code 500 (Internal Server Error).
     *
     * @param e the exception to handle
     * @return a {@link ResponseEntity} containing an {@link ApiError} with a HTTP 500 status code
     */
    @ExceptionHandler(UnexpectedApplicationException.class)
    public ResponseEntity<ApiError> handleUnexpectedApplicationException(UnexpectedApplicationException e) {
        log.warn("Resolved UnexpectedApplicationException: {}. " +
                "Consider exception hierarchy and exception handling in " +
                "domain and application layers to avoid this error", e.getMessage(), e);
        return new ResponseEntity<>(internalServerError(), INTERNAL_SERVER_ERROR);
    }
}
