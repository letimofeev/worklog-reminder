package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
@RequiredArgsConstructor
public class RestAdapterExceptionHandlerController {
    private final RestAdapterExceptionHandlerRegistry handlerRegistry;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleApplicationException(Exception ex) {
        return handlerRegistry.getHandler(ex.getClass())
                .map(handler -> handler.handleException(ex))
                .orElse(new ResponseEntity<>(new ApiError("Internal server error", INTERNAL_SERVER_ERROR.value()),
                        INTERNAL_SERVER_ERROR));
    }
}
