package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import com.senla.worklog.reminder.employee.application.exception.UnexpectedApplicationException;
import org.junit.jupiter.api.Test;

import java.lang.invoke.LambdaConversionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

class UnexpectedApplicationExceptionHandlerTest {
    private final UnexpectedApplicationExceptionHandler exceptionHandler = new UnexpectedApplicationExceptionHandler();

    @Test
    void handleException_shouldReturnNotFound_whenInputIsUnexpectedApplicationException() {
        var cause = new CloneNotSupportedException();
        var exception = new UnexpectedApplicationException(cause);

        var responseEntity = exceptionHandler.handleException(exception);

        assertEquals(responseEntity.getStatusCode(), INTERNAL_SERVER_ERROR);
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody().getStatus(), 500);
    }

    @Test
    void getExceptionType_shouldReturnUnexpectedApplicationExceptionClass() {
        assertEquals(UnexpectedApplicationException.class, exceptionHandler.getExceptionType());
    }

    @Test
    public void handleException_shouldReturn500_whenInputIsUnsupportedExceptionType() {
        var responseEntity = exceptionHandler.handleException(new LambdaConversionException());

        assertEquals(responseEntity.getStatusCode(), INTERNAL_SERVER_ERROR);
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody().getStatus(), 500);
    }
}