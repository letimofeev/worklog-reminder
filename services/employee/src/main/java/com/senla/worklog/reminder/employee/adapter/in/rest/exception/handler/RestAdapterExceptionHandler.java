package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError;
import org.springframework.http.ResponseEntity;

public interface RestAdapterExceptionHandler {
    ResponseEntity<ApiError> handleException(Exception ex);

    Class<? extends Throwable> getExceptionType();
}
