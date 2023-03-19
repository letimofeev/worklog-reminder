package com.senla.worklog.reminder.worklogdebt.adapter.out.rest.jira.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(METHOD)
@Retention(RUNTIME)
public @interface RefreshableSession {
    int maxRetries() default 1;
}
