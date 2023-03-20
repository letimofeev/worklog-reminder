package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError;
import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiSubError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

import static com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError.badRequest;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@Component
public class MethodArgumentTypeMismatchExceptionHandler extends AbstractRestAdapterExceptionHandler {
    @Override
    public ResponseEntity<ApiError> handleException(Exception e) {
        if (e instanceof MethodArgumentTypeMismatchException) {
            var castedEx = (MethodArgumentTypeMismatchException) e;
            log.debug("Resolved MethodArgumentTypeMismatchException: {}", castedEx.getMessage());
            var message = "Failed to parse request parameter with name " + castedEx.getName() +
                    " and value " + castedEx.getValue();
            var apiSubError = new ApiSubError(castedEx.getMostSpecificCause().getMessage());
            var apiError = badRequest(message, List.of(apiSubError));
            return new ResponseEntity<>(apiError, BAD_REQUEST);
        }
        return handleUnsupportedExceptionType(e);
    }

    @Override
    public Class<? extends Throwable> getExceptionType() {
        return MethodArgumentTypeMismatchException.class;
    }
}
