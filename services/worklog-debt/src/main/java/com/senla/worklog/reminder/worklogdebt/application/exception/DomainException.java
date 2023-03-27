package com.senla.worklog.reminder.worklogdebt.application.exception;

/**
 * This class is an abstract class that represents an exception that occurs within the domain layer of the application
 */
public abstract class DomainException extends RuntimeException {

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }

    public DomainException(String message) {
        super(message);
    }
}
