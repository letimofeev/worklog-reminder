package com.senla.worklog.reminder.employee.application.exception.wrapper;

import com.senla.worklog.reminder.employee.application.exception.ApplicationException;
import com.senla.worklog.reminder.employee.application.exception.UniqueConstraintViolationException;
import com.senla.worklog.reminder.employee.domain.exception.DuplicateAttributeException;
import org.springframework.stereotype.Component;

/**
 * This class is an implementation of the {@link ExceptionWrapper} interface.
 * It wraps a {@link DuplicateAttributeException} in an {@link UniqueConstraintViolationException} and returns it
 */
@Component
public class DuplicateAttributeExceptionWrapper implements ExceptionWrapper {

    /**
     Wraps a {@link DuplicateAttributeException} in an {@link UniqueConstraintViolationException}

     @param e The exception to wrap
     @return The wrapped exception
     @throws UnsupportedOperationException if an unsupported exception is passed to this method
     */
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

    /**
     * Returns the type of exception that this wrapper can wrap, which is {@link DuplicateAttributeException}.
     *
     * @return the {@link DuplicateAttributeException} class
     */
    @Override
    public Class<? extends Exception> getExceptionType() {
        return DuplicateAttributeException.class;
    }
}
