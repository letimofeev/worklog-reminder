package com.senla.worklog.reminder.employee.application.exception.wrapper;

import com.senla.common.exception.ApplicationException;
import com.senla.common.exception.wrapper.ExceptionWrapper;
import com.senla.worklog.reminder.employee.application.exception.ResourceNotFoundException;
import com.senla.worklog.reminder.employee.domain.exception.EmployeeNotFoundException;
import org.springframework.stereotype.Component;

/**
 * This class is an implementation of the {@link ExceptionWrapper} interface.
 * It wraps a {@link EmployeeNotFoundException} in an {@link ResourceNotFoundException} and returns it
 */
@Component
public class EmployeeNotFoundExceptionWrapper implements ExceptionWrapper {

    /**
     Wraps a {@link EmployeeNotFoundException} in an {@link ResourceNotFoundException}

     @param e The exception to wrap
     @return The wrapped exception
     @throws UnsupportedOperationException if an unsupported exception is passed to this method
     */
    @Override
    public ApplicationException wrapInApplicationException(Exception e) {
        if (e instanceof EmployeeNotFoundException) {
            var message = "Resource not found";
            var attributeName = ((EmployeeNotFoundException) e).attributeName();
            var attributeValue = ((EmployeeNotFoundException) e).attributeValue();
            return new ResourceNotFoundException(message, e, attributeName, attributeValue);
        }
        throw new UnsupportedOperationException("Unsupported exception was passed to " +
                "EmployeeNotFoundExceptionMapper: " + e.getClass().getSimpleName(), e);
    }

    /**
     * Returns the type of exception that this wrapper can wrap, which is {@link EmployeeNotFoundException}.
     *
     * @return the {@link EmployeeNotFoundException} class
     */
    @Override
    public Class<? extends Exception> getExceptionType() {
        return EmployeeNotFoundException.class;
    }
}
