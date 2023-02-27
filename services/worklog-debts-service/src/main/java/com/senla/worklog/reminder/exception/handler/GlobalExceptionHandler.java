package com.senla.worklog.reminder.exception.handler;

import com.senla.worklog.reminder.exception.EmployeeNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiError> handleEmployeeNotFound(EmployeeNotFoundException e) {
        log.warn("Resolved EmployeeNotFoundException: {}", e.getMessage());
        var apiError = new ApiError(e.getMessage(), NOT_FOUND);
        return new ResponseEntity<>(apiError, NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        log.warn("Resolved MethodArgumentTypeMismatchException: {}", e.getMessage());
        var message = "Failed to parse request parameter with name " + e.getName() +
                " and value " + e.getValue();
        var apiSubError = new ApiSubError(e.getMostSpecificCause().getMessage());
        var apiError = new ApiError(message, BAD_REQUEST, List.of(apiSubError));
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> handleMissingServletRequestParameter(MissingServletRequestParameterException e) {
        log.warn("Resolved MissingServletRequestParameterException: {}", e.getMessage());
        var message = "Missing value for parameter " + e.getParameterName();
        var apiError = new ApiError(message, BAD_REQUEST);
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException e) {
        log.warn("Resolved ConstraintViolationException: {}", e.getMessage());
        var message = "Validation failed";
        List<ApiSubError> subErrors = e.getConstraintViolations().stream()
                .map(violation -> new ValidationFailedApiSubError(violation.getMessage(),
                        violation.getInvalidValue()))
                .collect(toList());
        var apiError = new ApiError(message, BAD_REQUEST, subErrors);
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiError> handleBindException(BindException e) {
        log.warn("Resolved BindException: {}", e.getMessage());
        var message = "Validation failed";
        List<ApiSubError> subErrors = e.getFieldErrors().stream()
                .map(error -> new ValidationFailedApiSubError(resolveLocalizedErrorMessage(error),
                        error.getRejectedValue()))
                .collect(toList());
        var apiError = new ApiError(message, BAD_REQUEST, subErrors);
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAll(Exception e) {
        log.warn("Resolved {}: {}", e.getClass().getSimpleName(), e.getMessage());
        e.printStackTrace();
        var message = "Internal server error";
        var apiError = new ApiError(message, INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiError, INTERNAL_SERVER_ERROR);
    }

    private String resolveLocalizedErrorMessage(FieldError fieldError) {
        var currentLocale =  LocaleContextHolder.getLocale();
        return messageSource.getMessage(fieldError, currentLocale);
    }
}
