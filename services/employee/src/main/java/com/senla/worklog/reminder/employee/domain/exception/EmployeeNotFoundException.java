package com.senla.worklog.reminder.employee.domain.exception;

public class EmployeeNotFoundException extends DomainException {
    public EmployeeNotFoundException(String mainMessage) {
        super(mainMessage);
    }

    public EmployeeNotFoundException(Long employeeId) {
        super("Employee with id = '" + employeeId + "' not found");
    }
}
