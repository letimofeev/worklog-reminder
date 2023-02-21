package com.senla.worklog.reminder.validator;

import com.senla.worklog.reminder.annotation.DateFromMoreRecentThanDateTo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateFromMoreRecentThanDateToValidator implements ConstraintValidator<DateFromMoreRecentThanDateTo, GetAllDebtsForPeriodRequestParameters> {
    @Override
    public boolean isValid(GetAllDebtsForPeriodRequestParameters value, ConstraintValidatorContext context) {
        var dateFrom = value.getDateFrom();
        var dateTo = value.getDateTo();
        if (dateFrom.isAfter(dateTo)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(String.format("dateFrom (%s) is after dateTo (%s), which is invalid",
                            dateFrom, dateTo))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
