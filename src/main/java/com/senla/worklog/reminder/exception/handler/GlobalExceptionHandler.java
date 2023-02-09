package com.senla.worklog.reminder.exception.handler;

import com.senla.worklog.reminder.exception.EmployeeNotFoundException;
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
import java.util.Locale;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiError> handleEmployeeNotFound(EmployeeNotFoundException e) {
        log.warn("Resolved EmployeeNotFoundException: {}", e.getMessage());
        ApiError apiError = new ApiError(e.getMessage(), NOT_FOUND);
        return new ResponseEntity<>(apiError, NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        log.warn("Resolved MethodArgumentTypeMismatchException: {}", e.getMessage());
        String message = "Failed to parse request parameter with name " + e.getName() +
                " and value " + e.getValue();
        ApiSubError apiSubError = new ApiSubError(e.getMostSpecificCause().getMessage());
        ApiError apiError = new ApiError(message, BAD_REQUEST, List.of(apiSubError));
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> handleMissingServletRequestParameter(MissingServletRequestParameterException e) {
        log.warn("Resolved MissingServletRequestParameterException: {}", e.getMessage());
        String message = "Missing value for parameter " + e.getParameterName();
        ApiError apiError = new ApiError(message, BAD_REQUEST);
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException e) {
        log.warn("Resolved ConstraintViolationException: {}", e.getMessage());
        String message = "Validation failed";
        List<ApiSubError> subErrors = e.getConstraintViolations().stream()
                .map(violation -> new ValidationFailedApiSubError(violation.getMessage(), violation.getInvalidValue()))
                .collect(Collectors.toList());
        ApiError apiError = new ApiError(message, BAD_REQUEST, subErrors);
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiError> handleBindException(BindException e) {
        log.warn("Resolved BindException: {}", e.getMessage());
        String message = "Validation failed";
        List<ApiSubError> subErrors = e.getFieldErrors().stream()
                .map(error -> new ValidationFailedApiSubError(resolveLocalizedErrorMessage(error),
                        error.getRejectedValue()))
                .collect(Collectors.toList());
        ApiError apiError = new ApiError(message, BAD_REQUEST, subErrors);
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAll(Exception e) {
        log.warn("Resolved {}: {}", e.getClass().getSimpleName(), e.getMessage());
        e.printStackTrace();
        String message = "Internal server error";
        ApiError apiError = new ApiError(message, INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiError, INTERNAL_SERVER_ERROR);
    }

    private String resolveLocalizedErrorMessage(FieldError fieldError) {
        Locale currentLocale =  LocaleContextHolder.getLocale();
        return messageSource.getMessage(fieldError, currentLocale);
    }
}