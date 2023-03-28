package com.senla.worklog.reminder.exception;

/**
 * The ApplicationException class is an abstract base class for custom application exceptions. It extends
 * RuntimeException to indicate that any exceptions derived from this class are unchecked exceptions, and
 * it provides a constructor that takes a message and a cause.
 */
public abstract class ApplicationException extends RuntimeException {
    /**
     * Constructs a new ApplicationException with the specified detail message and cause.
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause The cause (which is saved for later retrieval by the getCause() method)
     */
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
