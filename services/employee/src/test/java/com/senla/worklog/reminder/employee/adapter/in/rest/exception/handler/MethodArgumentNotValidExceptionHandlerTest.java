package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

class MethodArgumentNotValidExceptionHandlerTest {
    private final MethodArgumentNotValidExceptionHandler exceptionHandler = new MethodArgumentNotValidExceptionHandler();

    @Test
    public void handleException_shouldReturn400_whenInputIsMethodArgumentNotValidException() {
        var exception = mock(MethodArgumentNotValidException.class);

        var responseEntity = exceptionHandler.handleException(exception);

        assertEquals(responseEntity.getStatusCode(), BAD_REQUEST);
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody().getMessage(), "Validation failed");
        assertEquals(responseEntity.getBody().getStatus(), 400);
    }

    @Test
    public void handleException_shouldReturn500_whenInputIsUnsupportedExceptionType() {
        var responseEntity = exceptionHandler.handleException(new LayerInstantiationException());

        assertEquals(responseEntity.getStatusCode(), INTERNAL_SERVER_ERROR);
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody().getStatus(), 500);
    }

    @Test
    public void getExceptionType_shouldReturnMethodArgumentNotValidException() {
        assertEquals(MethodArgumentNotValidException.class, exceptionHandler.getExceptionType());
    }

}
