package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError;
import com.senla.worklog.reminder.employee.adapter.in.rest.dto.AttributeApiSubError;
import com.senla.worklog.reminder.employee.application.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError.notFound;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * An exception handler for handling {@link ResourceNotFoundExceptionHandler}, which is thrown when the requested resource
 * not found in the system. This handler returns a HTTP 404 Not Found response with a user-friendly error message
 */
@Slf4j
@Component
public class ResourceNotFoundExceptionHandler extends AbstractRestAdapterExceptionHandler {

    /**
     * Handles the {@link ResourceNotFoundException} and returns a HTTP 404 Not Found response
     * with a user-friendly error message.
     * <p>
     * If the passed exception is not an instance of ResourceNotFoundException, it delegates
     * the handling to the parent class method handleUnsupportedExceptionType().
     *
     * @param e the {@link ResourceNotFoundException} to handle
     * @return a {@link ResponseEntity} containing an {@link ApiError} with a HTTP 404 status code and a
     * user-friendly error message
     */
    @Override
    public ResponseEntity<ApiError> handleException(Exception e) {
        if (e instanceof ResourceNotFoundException) {
            log.debug("Resolved ResourceNotFoundException: {}", e.getMessage());

            var castedEx = (ResourceNotFoundException) e;
            var subErrors = List.of(new AttributeApiSubError()
                    .setAttributeName(castedEx.attributeName())
                    .setAttributeValue(castedEx.attributeValue())
                    .setMessage(castedEx.causeMessage()));
            var apiError = notFound(castedEx.getMessage(), subErrors);

            return new ResponseEntity<>(apiError, NOT_FOUND);
        }
        return handleUnsupportedExceptionType(e);
    }

    /**
     * Returns the type of exception that this handler can handle, which is
     * {@link ResourceNotFoundException}.
     *
     * @return the {@link ResourceNotFoundException} class
     */
    @Override
    public Class<ResourceNotFoundException> getExceptionType() {
        return ResourceNotFoundException.class;
    }
}
