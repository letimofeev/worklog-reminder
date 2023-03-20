package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError;
import com.senla.worklog.reminder.employee.application.exception.UnexpectedApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError.internalServerError;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@Component
public class UnexpectedApplicationExceptionHandler extends AbstractRestAdapterExceptionHandler {
    @Override
    public ResponseEntity<ApiError> handleException(Exception ex) {
        if (ex instanceof UnexpectedApplicationException) {
            log.warn("Resolved UnexpectedApplicationException: {}. " +
                    "Consider exception hierarchy and exception handling in " +
                    "domain and application layers to avoid this error", ex.getMessage(), ex);
            return new ResponseEntity<>(internalServerError(), INTERNAL_SERVER_ERROR);
        }
        return handleUnsupportedExceptionType(ex);
    }

    @Override
    public Class<? extends Throwable> getExceptionType() {
        return UnexpectedApplicationException.class;
    }
}
