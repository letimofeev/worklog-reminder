package com.senla.worklog.reminder.employee.application.exception.mapper;

import com.senla.worklog.reminder.employee.application.exception.ApplicationException;
import com.senla.worklog.reminder.employee.application.exception.ResourceNotFoundException;
import com.senla.worklog.reminder.employee.domain.exception.DomainException;
import com.senla.worklog.reminder.employee.domain.exception.EmployeeNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class EmployeeNotFoundExceptionMapper implements DomainExceptionMapper {
    @Override
    public ApplicationException mapToApplicationException(DomainException ex) {
        var message = ex.mainMessage();
        return new ResourceNotFoundException(message, ex);
    }

    @Override
    public Class<? extends DomainException> getExceptionType() {
        return EmployeeNotFoundException.class;
    }
}
