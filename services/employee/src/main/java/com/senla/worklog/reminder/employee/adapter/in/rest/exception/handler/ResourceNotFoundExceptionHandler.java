package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError;
import com.senla.worklog.reminder.employee.application.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Component
public class ResourceNotFoundExceptionHandler implements RestAdapterExceptionHandler {
    @Override
    public ResponseEntity<ApiError> handleException(Exception ex) {
        if (ex instanceof ResourceNotFoundException) {
            log.debug("Resolved ResourceNotFoundException: {}", ex.getMessage());
            var apiError = new ApiError(((ResourceNotFoundException) ex).mainMessage(), NOT_FOUND.value());
            return new ResponseEntity<>(apiError, NOT_FOUND);
        }
        throw new UnsupportedOperationException("Unsupported exception was passed to " +
                "ResourceNotFoundExceptionHandler", ex);
    }

    @Override
    public Class<ResourceNotFoundException> getExceptionType() {
        return ResourceNotFoundException.class;
    }
}
