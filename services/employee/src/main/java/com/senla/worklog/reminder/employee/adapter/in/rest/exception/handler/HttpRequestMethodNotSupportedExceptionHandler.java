package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import static com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError.methodNotAllowed;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

@Slf4j
@Component
public class HttpRequestMethodNotSupportedExceptionHandler extends AbstractRestAdapterExceptionHandler {
    @Override
    public ResponseEntity<ApiError> handleException(Exception e) {
        if (e instanceof HttpRequestMethodNotSupportedException) {
            log.debug("Resolved HttpRequestMethodNotSupportedException: {}", e.getMessage());
            var message = e.getMessage();
            var apiError = methodNotAllowed(message);
            return new ResponseEntity<>(apiError, METHOD_NOT_ALLOWED);
        }
        return handleUnsupportedExceptionType(e);
    }

    @Override
    public Class<? extends Throwable> getExceptionType() {
        return HttpRequestMethodNotSupportedException.class;
    }
}
