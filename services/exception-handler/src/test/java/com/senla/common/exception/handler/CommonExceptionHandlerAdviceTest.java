package com.senla.common.exception.handler;

import com.senla.common.exception.handler.model.ApiError;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

class CommonExceptionHandlerAdviceTest {
    private final CommonExceptionHandlerAdvice exceptionHandler = new CommonExceptionHandlerAdvice();

    @Test
    void handleNoHandlerFoundException_shouldReturn404_whenInputIsNoHandlerFoundException() {
        var exception = new NoHandlerFoundException("GET", "/api/resource", null);

        var responseEntity = exceptionHandler.handleNoHandlerFoundException(exception, null, null, null);

        assertEquals(responseEntity.getStatusCode(), NOT_FOUND);
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof ApiError);

        var body = (ApiError) responseEntity.getBody();
        assertEquals(body.getMessage(), "No handler found for GET /api/resource");
        assertEquals(body.getStatus(), 404);
    }

    @Test
    void handleHttpRequestMethodNotSupported_shouldReturn405_whenInputIsHttpRequestMethodNotSupportedException() {
        var exception = new HttpRequestMethodNotSupportedException("method name");

        var responseEntity = exceptionHandler.handleHttpRequestMethodNotSupported(exception, null, null, null);

        assertEquals(responseEntity.getStatusCode(), METHOD_NOT_ALLOWED);
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof ApiError);

        var body = (ApiError) responseEntity.getBody();
        assertEquals(body.getMessage(), "Request method 'method name' not supported");
        assertEquals(body.getStatus(), 405);
    }

    @Test
    void handleMethodArgumentNotValid_shouldReturn400_whenInputIsMethodArgumentNotValidException() {
        var exception = mock(MethodArgumentNotValidException.class);

        var responseEntity = exceptionHandler.handleMethodArgumentNotValid(exception, null, null, null);

        assertEquals(responseEntity.getStatusCode(), BAD_REQUEST);
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof ApiError);

        var body = (ApiError) responseEntity.getBody();
        assertEquals(body.getMessage(), "Validation failed");
        assertEquals(body.getStatus(), 400);
    }

    @Test
    @SuppressWarnings("deprecation")
    void handleHttpMessageNotReadable_shouldReturn400_whenInputIsHttpMessageNotReadableException() {
        var exception = new HttpMessageNotReadableException("Could not read JSON");

        var responseEntity = exceptionHandler.handleHttpMessageNotReadable(exception, null, null, null);

        assertEquals(responseEntity.getStatusCode(), BAD_REQUEST);
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof ApiError);

        var body = (ApiError) responseEntity.getBody();
        assertEquals(body.getMessage(), "Failed to parse request body");
        assertEquals(body.getStatus(), 400);
    }

    @Test
    void handleMethodArgumentTypeMismatch_shouldReturn400_whenInputIsMethodArgumentTypeMismatchException() {
        var paramName = "id";
        var paramValue = "invalid-value";
        var exception = new MethodArgumentTypeMismatchException(paramValue, null, paramName, null, null);

        var responseEntity = exceptionHandler.handleMethodArgumentTypeMismatch(exception);

        assertEquals(responseEntity.getStatusCodeValue(), 400);
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof ApiError);

        var body = (ApiError) responseEntity.getBody();
        assertEquals(body.getStatus(), 400);
        assertThat(body.getMessage()).contains(paramName).contains(paramValue);
    }
}
