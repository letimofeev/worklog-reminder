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

@Slf4j
@Component
public class ResourceNotFoundExceptionHandler extends AbstractRestAdapterExceptionHandler {
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

    @Override
    public Class<ResourceNotFoundException> getExceptionType() {
        return ResourceNotFoundException.class;
    }
}
