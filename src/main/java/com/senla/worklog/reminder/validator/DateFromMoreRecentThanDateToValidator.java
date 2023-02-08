package com.senla.worklog.reminder.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateFromMoreRecentThanDateToValidator implements ConstraintValidator<DateFromMoreRecentThanDateTo, GetAllDebtsForPeriodRequestParameters> {
    @Override
    public boolean isValid(GetAllDebtsForPeriodRequestParameters value, ConstraintValidatorContext context) {
        LocalDate dateFrom = value.getDateFrom();
        LocalDate dateTo = value.getDateTo();
        if (dateFrom.isAfter(dateTo)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(String.format("'dateFrom' (%s) is after 'dateTo' (%s), which is invalid.",
                            dateFrom, dateTo))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
