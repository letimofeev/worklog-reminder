package com.senla.worklog.reminder.employee.application.exception.wrapper;

import com.senla.worklog.reminder.employee.application.exception.ApplicationException;
import com.senla.worklog.reminder.employee.application.exception.ResourceNotFoundException;
import com.senla.worklog.reminder.employee.domain.exception.EmployeeNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class EmployeeNotFoundExceptionWrapper implements ExceptionWrapper {
    @Override
    public ApplicationException wrapInApplicationException(Exception e) {
        if (e instanceof EmployeeNotFoundException) {
            var message = "Resource not found";
            var attributeName = ((EmployeeNotFoundException) e).attributeName();
            var attributeValue = ((EmployeeNotFoundException) e).attributeValue();
            return new ResourceNotFoundException(message, e, attributeName, attributeValue);
        }
        throw new UnsupportedOperationException("Unsupported exception was passed to " +
                "EmployeeNotFoundExceptionMapper: " + e.getClass().getSimpleName(), e);
    }

    @Override
    public Class<? extends Exception> getExceptionType() {
        return EmployeeNotFoundException.class;
    }
}
