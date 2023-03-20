package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError;
import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiSubError;
import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ValidationApiSubError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError.badRequest;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@Component
public class MethodArgumentNotValidExceptionHandler extends AbstractRestAdapterExceptionHandler {
    @Override
    public ResponseEntity<ApiError> handleException(Exception e) {
        if (e instanceof MethodArgumentNotValidException) {
            var ex = (MethodArgumentNotValidException) e;
            log.debug("Resolved BindException: {}", ex.getMessage());
            var message = "Validation failed";
            List<ApiSubError> subErrors = ex.getFieldErrors().stream()
                    .map(error -> new ValidationApiSubError(error.getDefaultMessage(),
                            error.getRejectedValue()))
                    .collect(toList());
            var apiError = badRequest(message, subErrors);
            return new ResponseEntity<>(apiError, BAD_REQUEST);
        }
        return handleUnsupportedExceptionType(e);
    }

    @Override
    public Class<? extends Throwable> getExceptionType() {
        return MethodArgumentNotValidException.class;
    }
}
