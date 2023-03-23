package com.senla.worklog.reminder.employee.application.exception.wrapper;

import com.senla.worklog.reminder.employee.application.exception.ResourceNotFoundException;
import com.senla.worklog.reminder.employee.domain.exception.EmployeeNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmployeeNotFoundExceptionWrapperTest {
    private final ExceptionWrapper wrapper = new EmployeeNotFoundExceptionWrapper();

    @Test
    void testWrapInApplicationException() {
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
    void testGetExceptionType() {
        assertEquals(EmployeeNotFoundException.class, wrapper.getExceptionType());
    }

    @Test
    void testWrapInApplicationExceptionWithUnsupportedException() {
        assertThrows(UnsupportedOperationException.class, () -> wrapper.wrapInApplicationException(new InstantiationException()));
    }
}
