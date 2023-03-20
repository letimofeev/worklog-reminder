package com.senla.worklog.reminder.employee.application.exception.wrapper;

import com.senla.worklog.reminder.employee.application.exception.ApplicationException;

public interface ExceptionWrapper {
    ApplicationException wrapInApplicationException(Exception e);

    Class<? extends Exception> getExceptionType();
}
