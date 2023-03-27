package com.senla.common.exception.handler;

import com.senla.common.exception.handler.model.ApiSubError;
import com.senla.common.exception.handler.model.AttributeApiSubError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static com.senla.common.exception.handler.model.ApiError.badRequest;
import static com.senla.common.exception.handler.model.ApiError.methodNotAllowed;
import static com.senla.common.exception.handler.model.ApiError.notFound;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
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

    @NonNull
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(@NonNull HttpRequestMethodNotSupportedException ex,
                                                                         @NonNull HttpHeaders headers,
                                                                         @NonNull HttpStatus status,
                                                                         @NonNull WebRequest request) {
        log.trace("Resolved HttpRequestMethodNotSupportedException: {}", ex.getMessage());

        var message = ex.getMessage();
        var apiError = methodNotAllowed(message);
        return new ResponseEntity<>(apiError, METHOD_NOT_ALLOWED);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        log.trace("Resolved MethodArgumentNotValidException: {}", ex.getMessage());

        var message = "Validation failed";
        List<ApiSubError> subErrors = ex.getFieldErrors().stream()
                .map(error -> new AttributeApiSubError(error.getDefaultMessage(),
                        error.getField(), String.valueOf(error.getRejectedValue())))
                .collect(toList());
        var apiError = badRequest(message, subErrors);

        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        log.trace("Resolved HttpMessageNotReadableException: {}", ex.getMessage());

        var message = "Failed to parse request body";
        var apiError = badRequest(message);
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(@NonNull MethodArgumentTypeMismatchException ex) {
        log.trace("Resolved MethodArgumentTypeMismatchException: {}", ex.getMessage());

        var message = "Failed to parse request parameter with name '" + ex.getName() +
                "' and value '" + ex.getValue() + "'";
        var apiError = badRequest(message);
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }
}
