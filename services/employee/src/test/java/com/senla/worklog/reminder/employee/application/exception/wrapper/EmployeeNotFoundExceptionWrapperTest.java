package com.senla.worklog.reminder.employee.application.exception.wrapper;

import com.senla.worklog.reminder.employee.application.exception.ResourceNotFoundException;
import com.senla.worklog.reminder.employee.domain.exception.EmployeeNotFoundException;
import com.senla.worklog.reminder.exception.wrapper.ExceptionWrapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmployeeNotFoundExceptionWrapperTest {
    private final ExceptionWrapper wrapper = new EmployeeNotFoundExceptionWrapper();

    @Test
    void wrapInApplicationException_shouldReturnResourceNotFoundException_whenInputIsEmployeeNotFoundException() {
        var attributeName = "id";
        var attributeValue = "123";
        var causeMessage = "Message";
        var cause = new EmployeeNotFoundException(causeMessage, attributeName, attributeValue);

        var actual = wrapper.wrapInApplicationException(cause);

        assertTrue(actual instanceof ResourceNotFoundException);
        assertEquals(cause, actual.getCause());
        assertEquals(attributeName, ((ResourceNotFoundException) actual).attributeName());
        assertEquals(attributeValue, ((ResourceNotFoundException) actual).attributeValue());
    }

    @Test
    void getExceptionType_shouldReturnEmployeeNotFoundException() {
        assertEquals(EmployeeNotFoundException.class, wrapper.getExceptionType());
    }

    @Test
    void wrapInApplicationException_shouldThrowException_whenInputIsUnsupportedException() {
        assertThrows(UnsupportedOperationException.class, () -> wrapper.wrapInApplicationException(new InstantiationException()));
    }
}
