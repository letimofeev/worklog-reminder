package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import org.junit.jupiter.api.Test;
import org.springframework.http.converter.HttpMessageNotReadableException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

class HttpMessageNotReadableExceptionHandlerTest {
    private final HttpMessageNotReadableExceptionHandler exceptionHandler = new HttpMessageNotReadableExceptionHandler();

    @Test
    @SuppressWarnings("deprecation")
    public void handleException_shouldReturn400_whenInputIsHttpMessageNotReadableException() {
        var exception = new HttpMessageNotReadableException("Could not read JSON");

        var responseEntity = exceptionHandler.handleException(exception);

        assertEquals(responseEntity.getStatusCode(), BAD_REQUEST);
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody().getMessage(), "Failed to parse request body");
        assertEquals(responseEntity.getBody().getStatus(), 400);
    }

    @Test
    public void handleException_shouldReturn500_whenInputIsUnsupportedExceptionType() {
        var responseEntity = exceptionHandler.handleException(new IllegalCallerException());

        assertEquals(responseEntity.getStatusCode(), INTERNAL_SERVER_ERROR);
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody().getStatus(), 500);
    }

    @Test
    public void getExceptionType_shouldReturnHttpMessageNotReadableException() {
        assertEquals(HttpMessageNotReadableException.class, exceptionHandler.getExceptionType());
    }
}
