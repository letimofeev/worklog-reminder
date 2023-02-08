package com.senla.worklog.reminder.exception.handler;

import com.senla.worklog.reminder.exception.ApiError;
import com.senla.worklog.reminder.exception.ApiSubError;
import com.senla.worklog.reminder.exception.EmployeeNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiError> handleException(EmployeeNotFoundException e) {
        ApiError apiError = new ApiError(e.getMessage(), NOT_FOUND);
        return new ResponseEntity<>(apiError, NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleException(MethodArgumentTypeMismatchException e) {
        String message = "Failed to parse request parameter with name '" + e.getName() +
                "' and value '" + e.getValue() + "'";
        ApiSubError apiSubError = new ApiSubError(e.getMostSpecificCause().getMessage());
        ApiError apiError = new ApiError(message, BAD_REQUEST, List.of(apiSubError));
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> handleException(MissingServletRequestParameterException e) {
        String message = "Missing value for parameter '" + e.getParameterName() + "'";
        ApiError apiError = new ApiError(message, BAD_REQUEST);
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }
}
