package com.senla.worklog.reminder.employee.adapter.in.rest.exception;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError.internalServerError;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class RestAdapterExceptionHandlerController {
    private final RestAdapterExceptionHandlerRegistry handlerRegistry;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleApplicationException(Exception e) {
        log.debug("Caught exception in rest adapter: {}", e.getClass());
        log.trace("Trying to find exception handler for exception: {}", e.getClass());
        return handlerRegistry.getHandler(e.getClass())
                .map(handler -> {
                    log.trace("Found handler '{}' for exception '{}'",
                            handler.getClass().getSimpleName(), e.getClass().getSimpleName());
                    return handler.handleException(e);
                })
                .orElseGet(() -> {
                    log.warn("RestAdapterExceptionHandler not found for exception: {}", e.getClass(), e);
                    return new ResponseEntity<>(internalServerError(), INTERNAL_SERVER_ERROR);
                });
    }
}