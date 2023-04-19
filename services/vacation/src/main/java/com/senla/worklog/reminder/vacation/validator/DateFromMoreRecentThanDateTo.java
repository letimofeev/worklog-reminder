package com.senla.worklog.reminder.vacation.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = DateFromMoreRecentThanDateToValidator.class)
public @interface DateFromMoreRecentThanDateTo {
    String message() default "'dateFrom' should be more recent than 'dateTo'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
