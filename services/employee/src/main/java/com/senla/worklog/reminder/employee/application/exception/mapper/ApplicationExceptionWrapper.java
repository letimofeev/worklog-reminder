package com.senla.worklog.reminder.employee.application.exception.mapper;

import com.senla.worklog.reminder.employee.application.exception.ApplicationException;

public interface ApplicationExceptionWrapper {
    ApplicationException wrapInApplicationException(Exception ex);

    Class<? extends Exception> getExceptionType();
}
