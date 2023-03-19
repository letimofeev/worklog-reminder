package com.senla.worklog.reminder.employee.application.exception.mapper;

import com.senla.worklog.reminder.employee.application.exception.ApplicationException;
import com.senla.worklog.reminder.employee.domain.exception.DomainException;

public interface DomainExceptionMapper {
    ApplicationException mapToApplicationException(DomainException ex);

    Class<? extends DomainException> getExceptionType();
}
