package com.senla.worklog.reminder.employee.application.exception.wrapper;

import com.senla.worklog.reminder.employee.application.exception.UniqueConstraintViolationException;
import com.senla.worklog.reminder.employee.domain.exception.DuplicateAttributeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DuplicateAttributeExceptionWrapperTest {
    private final DuplicateAttributeExceptionWrapper wrapper = new DuplicateAttributeExceptionWrapper();

    @Test
    void wrapInApplicationException_shouldReturnDuplicateAttributeException_whenInputIsUniqueConstraintViolationException() {
        String message = "Uniqueness error";
        String attributeName = "email";
        String attributeValue = "jane.doe@example.com";
        var cause = new DuplicateAttributeException(message, attributeName, attributeValue);

        var actual = wrapper.wrapInApplicationException(cause);

        assertTrue(actual instanceof UniqueConstraintViolationException);
        assertEquals(message, actual.getMessage());
        assertEquals(cause, actual.getCause());
        assertEquals(attributeName, ((UniqueConstraintViolationException) actual).attributeName());
        assertEquals(attributeValue, ((UniqueConstraintViolationException) actual).attributeValue());
    }

    @Test
    void getExceptionType_shouldReturnDuplicateAttributeExceptionClass() {
        assertEquals(DuplicateAttributeException.class, wrapper.getExceptionType());
    }

    @Test
    void testWrapInApplicationException_shouldThrowException_whenInputIsUnsupportedException() {
        assertThrows(UnsupportedOperationException.class, () -> wrapper.wrapInApplicationException(new RuntimeException()));
    }
}