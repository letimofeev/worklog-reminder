package com.senla.common.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.senla.common.exception.handler.model.ApiError.notFound;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Configuration
@ControllerAdvice
public class CommonExceptionHandlerAdvice extends ResponseEntityExceptionHandler {
    @NonNull
    @Override
    public ResponseEntity<Object> handleNoHandlerFoundException(@NonNull NoHandlerFoundException ex,
                                                                @NonNull HttpHeaders headers,
                                                                @NonNull HttpStatus status,
                                                                @NonNull WebRequest request) {
        log.trace("Resolved NoHandlerFoundException: {}", ex.getMessage());
        var message = ex.getMessage();
        var apiError = notFound(message);
        return new ResponseEntity<>(apiError, NOT_FOUND);
    }
}
