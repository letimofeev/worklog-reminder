package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.AttributeApiSubError;
import com.senla.worklog.reminder.employee.application.exception.UniqueConstraintViolationException;
import com.senla.worklog.reminder.employee.domain.exception.DuplicateAttributeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

class UniqueConstraintViolationExceptionHandlerTest {
    private final UniqueConstraintViolationExceptionHandler exceptionHandler = new UniqueConstraintViolationExceptionHandler();

    @Test
    public void handleException_shouldReturnBadRequest_whenInputIsUniqueConstraintViolationException() {
        var message = "Duplicate entry for username 'john'";
        var attributeName = "username";
        var attributeValue = "john";
        var cause = new DuplicateAttributeException(attributeName, attributeValue);
        var exception = new UniqueConstraintViolationException(message, cause, attributeName, attributeValue);

        var response = exceptionHandler.handleException(exception);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Duplicate entry for username 'john'", response.getBody().getMessage());
        assertEquals(400, response.getBody().getStatus());

        assertNotNull(response.getBody().getErrors());
        assertEquals(1, response.getBody().getErrors().size());

        var subError = response.getBody().getErrors().get(0);
        assertEquals("username", ((AttributeApiSubError) subError).getAttributeName());
        assertEquals("john", ((AttributeApiSubError) subError).getAttributeValue());
    }

    @Test
    void getExceptionType_shouldReturnUniqueConstraintViolationExceptionClass() {
        assertEquals(UniqueConstraintViolationException.class, exceptionHandler.getExceptionType());
    }

    @Test
    public void handleException_shouldReturn500_whenInputIsUnsupportedExceptionType() {
        var responseEntity = exceptionHandler.handleException(new RuntimeException());

        assertEquals(responseEntity.getStatusCode(), INTERNAL_SERVER_ERROR);
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody().getStatus(), 500);
    }
}
