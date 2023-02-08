package com.senla.worklog.reminder.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateFromMoreRecentThanDateToValidator.class)
public @interface DateFromMoreRecentThanDateTo {
    String message() default "`dateFrom` should be more recent than `dateTo`";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
