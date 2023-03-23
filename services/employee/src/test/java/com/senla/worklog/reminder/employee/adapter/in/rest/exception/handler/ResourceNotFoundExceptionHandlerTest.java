package com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.AttributeApiSubError;
import com.senla.worklog.reminder.employee.application.exception.ResourceNotFoundException;
import com.senla.worklog.reminder.employee.domain.exception.EmployeeNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

class ResourceNotFoundExceptionHandlerTest {
    private final ResourceNotFoundExceptionHandler exceptionHandler = new ResourceNotFoundExceptionHandler();

    @Test
    void handleException_shouldReturnNotFound_whenInputIsResourceNotFoundException() {
        var attributeName = "id";
        var attributeValue = "123";
        var cause = new EmployeeNotFoundException(123L);
        var exception = new ResourceNotFoundException("not found", cause, attributeName, attributeValue);

        var response = exceptionHandler.handleException(exception);

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
    void getExceptionType_shouldReturnResourceNotFoundExceptionClass() {
        assertEquals(ResourceNotFoundException.class, exceptionHandler.getExceptionType());
    }

    @Test
    public void handleException_shouldReturn500_whenInputIsUnsupportedExceptionType() {
        var responseEntity = exceptionHandler.handleException(new IllegalStateException());

        assertEquals(responseEntity.getStatusCode(), INTERNAL_SERVER_ERROR);
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody().getStatus(), 500);
    }
}
