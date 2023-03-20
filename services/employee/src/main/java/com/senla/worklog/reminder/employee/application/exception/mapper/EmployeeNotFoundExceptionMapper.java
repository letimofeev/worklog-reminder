package com.senla.worklog.reminder.employee.application.exception.mapper;

import com.senla.worklog.reminder.employee.application.exception.ApplicationException;
import com.senla.worklog.reminder.employee.application.exception.ResourceNotFoundException;
import com.senla.worklog.reminder.employee.domain.exception.EmployeeNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class EmployeeNotFoundExceptionMapper implements ApplicationExceptionWrapper {
    @Override
    public ApplicationException wrapInApplicationException(Exception ex) {
        if (ex instanceof EmployeeNotFoundException) {
            var message = "Resource not found";
            var attributeName = ((EmployeeNotFoundException) ex).getAttributeName();
            var attributeValue = ((EmployeeNotFoundException) ex).getAttributeValue();
            return new ResourceNotFoundException(message, ex, attributeName, attributeValue);
        }
        throw new UnsupportedOperationException("Unsupported exception was passed to " +
                "EmployeeNotFoundExceptionMapper: " + ex.getClass().getSimpleName(), ex);
    }

    @Override
    public Class<? extends Exception> getExceptionType() {
        return EmployeeNotFoundException.class;
    }
}
