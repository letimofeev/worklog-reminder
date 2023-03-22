package com.senla.worklog.reminder.employee.application.exception;

import com.senla.worklog.reminder.employee.application.exception.wrapper.ExceptionWrapper;

/**
 * This class represents an exception that is thrown when exception in
 * application service was not wrapped by specific {@link ExceptionWrapper} into corresponding {@link ApplicationException}
 */
public class UnexpectedApplicationException extends ApplicationException {
    public UnexpectedApplicationException(Throwable cause) {
        super("Unexpected application error", cause);
    }

    public UnexpectedApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
