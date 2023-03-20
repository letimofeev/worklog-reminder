package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;

import static com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError.badRequest;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@Component
public class HttpMessageNotReadableExceptionHandler extends AbstractRestAdapterExceptionHandler {
    @Override
    public ResponseEntity<ApiError> handleException(Exception e) {
        if (e instanceof HttpMessageNotReadableException) {
            var castedEx = (HttpMessageNotReadableException) e;
            log.debug("Resolved HttpMessageNotReadableExceptionHandler: {}", castedEx.getMessage());
            var message = "Failed to parse request body";
            var apiError = badRequest(message);
            return new ResponseEntity<>(apiError, BAD_REQUEST);
        }
        return handleUnsupportedExceptionType(e);
    }

    @Override
    public Class<? extends Throwable> getExceptionType() {
        return HttpMessageNotReadableException.class;
    }
}
