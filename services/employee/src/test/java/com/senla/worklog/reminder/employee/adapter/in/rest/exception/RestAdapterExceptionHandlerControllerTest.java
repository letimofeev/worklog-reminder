package com.senla.worklog.reminder.employee.adapter.in.rest.exception;

import com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler.HttpMessageNotReadableExceptionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ExtendWith(MockitoExtension.class)
class RestAdapterExceptionHandlerControllerTest {
    @Mock
    private RestAdapterExceptionHandlerRegistry handlerRegistry;

    @InjectMocks
    private RestAdapterExceptionHandlerController controller;

    @Test
    @SuppressWarnings("deprecation")
    void handleApplicationException_shouldReturnBadRequest_whenInputIsHttpMessageNotReadableException() {
        var exception = new HttpMessageNotReadableException("Error");

        when(handlerRegistry.getHandler(HttpMessageNotReadableException.class))
                .thenReturn(Optional.of(new HttpMessageNotReadableExceptionHandler()));

        var response = controller.handleApplicationException(exception);

        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void handleApplicationException_shouldReturnInternalServerError_whenInputIsUnsupportedExceptionType() {
        var exception = new Exception();

        when(handlerRegistry.getHandler(Exception.class)).thenReturn(Optional.empty());

        var response = controller.handleApplicationException(exception);

        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
