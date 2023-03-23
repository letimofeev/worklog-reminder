package com.senla.worklog.reminder.employee.application.exception.wrapper;

import com.senla.worklog.reminder.employee.application.exception.ApplicationException;

/**
 * An interface for classes that wrap exceptions in application-specific exceptions.
 */
public interface ExceptionWrapper {

    /**
     * Wraps the given exception in a specific {@link ApplicationException}.
     *
     * @param e the exception to wrap
     * @return the wrapped exception
     */
    ApplicationException wrapInApplicationException(Exception e);

    /**
     * Gets the class of the exception type that this wrapper handles.
     *
     * @return the class of the handled exception type
     */
    Class<? extends Exception> getExceptionType();
}
