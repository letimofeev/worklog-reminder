package com.senla.worklog.reminder.employee.application.exception.wrapper;

import com.senla.worklog.reminder.employee.application.exception.ApplicationException;
import com.senla.worklog.reminder.employee.application.exception.UniqueConstraintViolationException;
import com.senla.worklog.reminder.employee.domain.exception.DuplicateAttributeException;
import org.springframework.stereotype.Component;

@Component
public class DuplicateAttributeExceptionWrapper implements ExceptionWrapper {
    @Override
    public ApplicationException wrapInApplicationException(Exception e) {
        if (e instanceof DuplicateAttributeException) {
            var message = "Uniqueness error";
            var attributeName = ((DuplicateAttributeException) e).attributeName();
            var attributeValue = ((DuplicateAttributeException) e).attributeValue();
            return new UniqueConstraintViolationException(message, e, attributeName, attributeValue);
        }
        throw new UnsupportedOperationException("Unsupported exception was passed to " +
                "DuplicateAttributeExceptionWrapper: " + e.getClass().getSimpleName(), e);
    }

    @Override
    public Class<? extends Exception> getExceptionType() {
        return DuplicateAttributeException.class;
    }
}
