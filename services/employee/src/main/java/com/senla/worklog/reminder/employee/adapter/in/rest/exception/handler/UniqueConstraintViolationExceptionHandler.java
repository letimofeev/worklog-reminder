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

@Slf4j
@Component
public class UniqueConstraintViolationExceptionHandler extends AbstractRestAdapterExceptionHandler {
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

    @Override
    public Class<? extends Throwable> getExceptionType() {
        return UniqueConstraintViolationException.class;
    }
}
