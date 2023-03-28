package com.senla.worklog.reminder.exception.handler;

import com.senla.worklog.reminder.exception.UnexpectedApplicationException;
import com.senla.worklog.reminder.exception.handler.model.ApiError;
import com.senla.worklog.reminder.exception.handler.model.ApiSubError;
import com.senla.worklog.reminder.exception.handler.model.AttributeApiSubError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.util.MimeType;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static com.senla.worklog.reminder.exception.handler.model.ApiError.badRequest;
import static com.senla.worklog.reminder.exception.handler.model.ApiError.internalServerError;
import static com.senla.worklog.reminder.exception.handler.model.ApiError.methodNotAllowed;
import static com.senla.worklog.reminder.exception.handler.model.ApiError.notAcceptable;
import static com.senla.worklog.reminder.exception.handler.model.ApiError.notFound;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Class for handling exceptions thrown by controllers
 */
@Slf4j
@Configuration
@ControllerAdvice
@RequiredArgsConstructor
public class CommonExceptionHandlerAdvice extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    @NonNull
    @Override
    public ResponseEntity<Object> handleNoHandlerFoundException(@NonNull NoHandlerFoundException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        log.trace("Resolved NoHandlerFoundException: {}", ex.getMessage());
        var message = ex.getMessage();
        var apiError = notFound(message);
        return new ResponseEntity<>(apiError, NOT_FOUND);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(@NonNull HttpRequestMethodNotSupportedException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        log.trace("Resolved HttpRequestMethodNotSupportedException: {}", ex.getMessage());
        var message = ex.getMessage();
        var apiError = methodNotAllowed(message);
        return new ResponseEntity<>(apiError, METHOD_NOT_ALLOWED);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        log.trace("Resolved MethodArgumentNotValidException: {}", ex.getMessage());
        return handleBindExceptionInternal(ex);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleBindException(@NonNull BindException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        log.trace("Resolved BindException: {}", ex.getMessage());
        return handleBindExceptionInternal(ex);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        log.trace("Resolved HttpMessageNotReadableException: {}", ex.getMessage());
        var message = "Failed to parse request body";
        var apiError = badRequest(message);
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(@NonNull MissingServletRequestParameterException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        log.trace("Resolved MissingServletRequestParameterException: {}", ex.getMessage());
        var message = "Missing value for parameter '" + ex.getParameterName() + "'";
        var apiError = badRequest(message);
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(@NonNull TypeMismatchException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        log.trace("Resolved TypeMismatchException: {}", ex.getMessage());
        if (ex instanceof MethodArgumentTypeMismatchException) {
            var parameterName = ((MethodArgumentTypeMismatchException) ex).getName();
            var message = "Failed to parse request parameter with name = '" + parameterName +
                    "' and value = '" + ex.getValue() + "'";
            var apiError = badRequest(message);
            return new ResponseEntity<>(apiError, BAD_REQUEST);
        }
        var message = "Type mismatch for property with name = '" + ex.getPropertyName() +
                "' and value = '" + ex.getPropertyName() + "'";
        var apiError = badRequest(message);
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(@NonNull HttpMediaTypeNotAcceptableException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        log.trace("Resolved HttpMediaTypeNotAcceptableException: {}", ex.getMessage());
        var supportedTypes = ex.getSupportedMediaTypes().stream()
                .map(MimeType::getType)
                .collect(joining());
        var message = "Not acceptable media type, supported types: " + supportedTypes;
        var apiError = notAcceptable(message);
        return new ResponseEntity<>(apiError, NOT_ACCEPTABLE);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex) {
        log.trace("Resolved ConstraintViolationException: {}", ex.getMessage());
        var message = "Validation failed";
        List<ApiSubError> subErrors = ex.getConstraintViolations().stream()
                .map(violation -> new AttributeApiSubError(violation.getMessage(),
                        violation.getPropertyPath().toString(),
                        String.valueOf(violation.getInvalidValue())))
                .collect(toList());
        var apiError = badRequest(message, subErrors);
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(@NonNull MethodArgumentTypeMismatchException ex) {
        log.trace("Resolved MethodArgumentTypeMismatchException: {}", ex.getMessage());
        var message = "Failed to parse request parameter with name = '" + ex.getName() +
                "' and value = '" + ex.getValue() + "'";
        var apiError = badRequest(message);
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(UnexpectedApplicationException.class)
    public ResponseEntity<ApiError> handleUnexpectedApplicationException(UnexpectedApplicationException e) {
        log.warn("Resolved UnexpectedApplicationException: {}. " +
                "Consider exception hierarchy and exception handling in " +
                "domain and application layers to avoid this error", e.getMessage(), e);
        return new ResponseEntity<>(internalServerError(), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        log.warn("@ExceptionHandler not found for exception: {}", ex.getClass(), ex);
        return new ResponseEntity<>(internalServerError(), INTERNAL_SERVER_ERROR);
    }

    @NonNull
    private ResponseEntity<Object> handleBindExceptionInternal(BindException ex) {
        var message = "Validation failed";
        List<ApiSubError> subErrors = ex.getFieldErrors().stream()
                .map(error -> new AttributeApiSubError(resolveLocalizedErrorMessage(error),
                        error.getField(), String.valueOf(error.getRejectedValue())))
                .collect(toList());
        var globalSubErrors = ex.getGlobalErrors().stream()
                .map(error -> new ApiSubError(resolveLocalizedErrorMessage(error)))
                .collect(toList());
        subErrors.addAll(globalSubErrors);

        var apiError = badRequest(message, subErrors);

        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    private String resolveLocalizedErrorMessage(ObjectError error) {
        var currentLocale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(error, currentLocale);
    }
}
