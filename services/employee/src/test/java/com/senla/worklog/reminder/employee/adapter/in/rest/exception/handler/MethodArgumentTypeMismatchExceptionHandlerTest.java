package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import org.junit.jupiter.api.Test;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

class MethodArgumentTypeMismatchExceptionHandlerTest {
    private final MethodArgumentTypeMismatchExceptionHandler exceptionHandler = new MethodArgumentTypeMismatchExceptionHandler();

    @Test
    void handleException_shouldReturnBadRequest_whenInputIsMethodArgumentTypeMismatchException() {
        var paramName = "id";
        var paramValue = "invalid-value";
        var exception = new MethodArgumentTypeMismatchException(paramValue, null, paramName, null, null);

        var response = exceptionHandler.handleException(exception);

        assertEquals(response.getStatusCodeValue(), 400);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getStatus(), 400);
        assertThat(response.getBody().getMessage()).contains(paramName).contains(paramValue);
    }

    @Test
    void getExceptionType_shouldReturnMethodArgumentTypeMismatchExceptionClass() {
        assertEquals(MethodArgumentTypeMismatchException.class, exceptionHandler.getExceptionType());
    }

    @Test
    public void handleException_shouldReturn500_whenInputIsUnsupportedExceptionType() {
        var responseEntity = exceptionHandler.handleException(new LayerInstantiationException());

        assertEquals(responseEntity.getStatusCode(), INTERNAL_SERVER_ERROR);
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody().getStatus(), 500);
    }
}
