package com.senla.worklog.reminder.exception.handler;

import com.senla.worklog.reminder.exception.ApiError;
import com.senla.worklog.reminder.exception.EmployeeNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiError> handleException(EmployeeNotFoundException e) {
        ApiError apiError = new ApiError(e.getMessage(), NOT_FOUND);
        return new ResponseEntity<>(apiError, NOT_FOUND);
    }
}
