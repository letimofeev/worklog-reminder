package com.senla.worklog.reminder.exception.wrapper.annotation;


import com.senla.worklog.reminder.exception.ApplicationException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated class should be wrapped into corresponding {@link ApplicationException}
 * if an exception is thrown during its execution
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WrappedInApplicationException {
}
