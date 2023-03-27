package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import com.senla.common.exception.handler.model.AttributeApiSubError;
import com.senla.worklog.reminder.employee.application.exception.ResourceNotFoundException;
import com.senla.worklog.reminder.employee.application.exception.UnexpectedApplicationException;
import com.senla.worklog.reminder.employee.application.exception.UniqueConstraintViolationException;
import com.senla.worklog.reminder.employee.domain.exception.DuplicateAttributeException;
import com.senla.worklog.reminder.employee.domain.exception.EmployeeNotFoundException;
import org.junit.jupiter.api.Test;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

class RestAdapterExceptionHandlerTest {
    private final RestAdapterExceptionHandler exceptionHandler = new RestAdapterExceptionHandler();

    @Test
    public void handleResourceNotFoundException_shouldReturn405_whenInputIsResourceNotFoundException() {
        var attributeName = "id";
        var attributeValue = "123";
        var cause = new EmployeeNotFoundException(123L);
        var exception = new ResourceNotFoundException("not found", cause, attributeName, attributeValue);

        var response = exceptionHandler.handleResourceNotFoundException(exception);

        assertEquals(NOT_FOUND, response.getStatusCode());

        var responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("not found", responseBody.getMessage());
        assertEquals(404, responseBody.getStatus());
        assertEquals(1, responseBody.getErrors().size());

        var subError = responseBody.getErrors().get(0);
        assertTrue(subError instanceof AttributeApiSubError);

        var attributeSubError = (AttributeApiSubError) subError;
        assertEquals(attributeName, attributeSubError.getAttributeName());
        assertEquals(attributeValue, attributeSubError.getAttributeValue());
    }

    @Test
    public void handleUniqueConstraintViolationException_shouldReturn400_whenInputIsUniqueConstraintViolationException() {
        var message = "Duplicate entry for username 'john'";
        var attributeName = "username";
        var attributeValue = "john";
        var cause = new DuplicateAttributeException(attributeName, attributeValue);
        var exception = new UniqueConstraintViolationException(message, cause, attributeName, attributeValue);

        var response = exceptionHandler.handleUniqueConstraintViolationException(exception);

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
    void handleUnexpectedApplicationException_shouldReturn500_whenInputIsUnexpectedApplicationException() {
        var cause = new CloneNotSupportedException();
        var exception = new UnexpectedApplicationException(cause);

        var responseEntity = exceptionHandler.handleUnexpectedApplicationException(exception);

        assertEquals(responseEntity.getStatusCode(), INTERNAL_SERVER_ERROR);
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody().getStatus(), 500);
    }
}
