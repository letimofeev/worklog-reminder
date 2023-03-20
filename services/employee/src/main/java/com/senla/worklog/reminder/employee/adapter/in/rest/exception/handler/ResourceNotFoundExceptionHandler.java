package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError;
import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ResourceNotFoundApiSubError;
import com.senla.worklog.reminder.employee.application.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Component
public class ResourceNotFoundExceptionHandler extends AbstractRestAdapterExceptionHandler {
    @Override
    public ResponseEntity<ApiError> handleException(Exception ex) {
        if (ex instanceof ResourceNotFoundException) {
            log.debug("Resolved ResourceNotFoundException: {}", ex.getMessage());

            var castedEx = (ResourceNotFoundException) ex;
            var subErrors = List.of(new ResourceNotFoundApiSubError()
                    .setAttributeName(castedEx.attributeName())
                    .setAttributeValue(castedEx.attributeValue())
                    .setMessage(castedEx.causeMessage()));
            var apiError = new ApiError(castedEx.getMessage(), NOT_FOUND.value(), subErrors);

            return new ResponseEntity<>(apiError, NOT_FOUND);
        }
        return handleUnsupportedExceptionType(ex);
    }

    @Override
    public Class<ResourceNotFoundException> getExceptionType() {
        return ResourceNotFoundException.class;
    }
}
