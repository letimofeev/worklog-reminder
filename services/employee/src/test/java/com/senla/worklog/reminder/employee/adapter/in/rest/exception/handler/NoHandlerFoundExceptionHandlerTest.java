package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

class NoHandlerFoundExceptionHandlerTest {
    private final NoHandlerFoundExceptionHandler exceptionHandler = new NoHandlerFoundExceptionHandler();

    @Test
    public void handleException_shouldReturn405_whenInputIsNoHandlerFoundException() {
        var exception = new NoHandlerFoundException("GET", "/api/resource", null);

        var responseEntity = exceptionHandler.handleException(exception);

        assertEquals(responseEntity.getStatusCode(), NOT_FOUND);
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody().getMessage(), "No handler found for GET /api/resource");
        assertEquals(responseEntity.getBody().getStatus(), 404);
    }

    @Test
    public void handleException_shouldReturn500_whenInputIsUnsupportedExceptionType() {
        var responseEntity = exceptionHandler.handleException(new IllegalCallerException());

        assertEquals(responseEntity.getStatusCode(), INTERNAL_SERVER_ERROR);
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody().getStatus(), 500);
    }

    @Test
    public void getExceptionType_shouldReturnNoHandlerFoundException() {
        assertEquals(NoHandlerFoundException.class, exceptionHandler.getExceptionType());
    }
}
