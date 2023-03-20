package com.senla.worklog.reminder.employee.application.exception.wrapper;

import com.senla.worklog.reminder.employee.application.exception.ApplicationException;

public interface ExceptionWrapper {
    ApplicationException wrapInApplicationException(Exception ex);

    Class<? extends Exception> getExceptionType();
}
