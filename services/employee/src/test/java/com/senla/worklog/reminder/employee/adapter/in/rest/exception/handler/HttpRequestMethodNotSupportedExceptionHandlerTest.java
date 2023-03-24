package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import org.junit.jupiter.api.Test;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

class HttpRequestMethodNotSupportedExceptionHandlerTest {
    private final HttpRequestMethodNotSupportedExceptionHandler exceptionHandler = new HttpRequestMethodNotSupportedExceptionHandler();

    @Test
    public void handleException_shouldReturn405_whenInputIsHttpRequestMethodNotSupportedException() {
        var exception = new HttpRequestMethodNotSupportedException("method name");

        var responseEntity = exceptionHandler.handleException(exception);

        assertEquals(responseEntity.getStatusCode(), METHOD_NOT_ALLOWED);
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody().getMessage(), "Request method 'method name' not supported");
        assertEquals(responseEntity.getBody().getStatus(), 405);
    }

    @Test
    public void handleException_shouldReturn500_whenInputIsUnsupportedExceptionType() {
        var responseEntity = exceptionHandler.handleException(new IllegalCallerException());

        assertEquals(responseEntity.getStatusCode(), INTERNAL_SERVER_ERROR);
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody().getStatus(), 500);
    }

    @Test
    public void getExceptionType_shouldReturnHttpRequestMethodNotSupportedException() {
        assertEquals(HttpRequestMethodNotSupportedException.class, exceptionHandler.getExceptionType());
    }
}
