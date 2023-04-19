package com.senla.worklog.reminder.employee.application.exception.wrapper;

import com.senla.worklog.reminder.employee.application.exception.ResourceNotFoundException;
import com.senla.worklog.reminder.employee.domain.exception.RegionNotFoundException;
import com.senla.worklog.reminder.exception.ApplicationException;
import com.senla.worklog.reminder.exception.wrapper.ExceptionWrapper;
import org.springframework.stereotype.Component;

/**
 * This class is an implementation of the {@link ExceptionWrapper} interface.
 * It wraps a {@link RegionNotFoundException} in an {@link ResourceNotFoundException} and returns it
 */
@Component
public class RegionNotFoundExceptionWrapper implements ExceptionWrapper {

    /**
     Wraps a {@link RegionNotFoundException} in an {@link ResourceNotFoundException}

     @param e The exception to wrap
     @return The wrapped exception
     @throws UnsupportedOperationException if an unsupported exception is passed to this method
     */
    @Override
    public ApplicationException wrapInApplicationException(Exception e) {
        if (e instanceof RegionNotFoundException) {
            var message = "Resource not found";
            var attributeName = "regionId";
            var attributeValue = ((RegionNotFoundException) e).attributeValue();
            return new ResourceNotFoundException(message, e, attributeName, attributeValue);
        }
        throw new UnsupportedOperationException("Unsupported exception was passed to " +
                "RegionNotFoundExceptionWrapper: " + e.getClass().getSimpleName(), e);
    }

    /**
     * Returns the type of exception that this wrapper can wrap, which is {@link RegionNotFoundException}.
     *
     * @return the {@link RegionNotFoundException} class
     */
    @Override
    public Class<? extends Exception> getExceptionType() {
        return RegionNotFoundException.class;
    }
}
