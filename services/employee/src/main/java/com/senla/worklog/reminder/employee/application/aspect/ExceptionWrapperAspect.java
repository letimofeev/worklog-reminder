package com.senla.worklog.reminder.employee.application.aspect;

import com.senla.worklog.reminder.employee.application.annotation.WrappedInApplicationException;
import com.senla.worklog.reminder.employee.application.exception.ApplicationException;
import com.senla.worklog.reminder.employee.application.exception.UnexpectedApplicationException;
import com.senla.worklog.reminder.employee.application.exception.wrapper.ExceptionWrapperRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * AOP aspect for wrapping exceptions thrown by methods annotated with {@link WrappedInApplicationException}
 * into corresponding {@link ApplicationException}.
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ExceptionWrapperAspect {
    private final ExceptionWrapperRegistry wrapperRegistry;

    /**
     * Wraps the method execution in a try-catch block, and if an exception is thrown,
     * attempts to find an appropriate exception wrapper to wrap the exception into an {@link ApplicationException}.
     * If no suitable wrapper is found, creates a new {@link UnexpectedApplicationException}.
     *
     * @param joinPoint the join point representing the method execution.
     * @return the result of the method execution, or a wrapped exception if an exception was thrown.
     * @throws Throwable if the method throws an exception that could not be wrapped.
     */
    @Around("@within(com.senla.worklog.reminder.employee.application.annotation.WrappedInApplicationException)")
    public Object wrapMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            log.debug("Caught exception in application service: {}", e.getClass());
            log.trace("Trying to find application exception wrapper for exception: {}", e.getClass());
            throw wrapperRegistry.getMapper(e.getClass())
                    .map(wrapper -> {
                        log.trace("Found wrapper '{}' for exception '{}'",
                                wrapper.getClass().getSimpleName(), e.getClass().getSimpleName());
                        return wrapper.wrapInApplicationException(e);
                    })
                    .orElseGet(() -> {
                        log.warn("ExceptionWrapper not found for exception: {}", e.getClass());
                        return new UnexpectedApplicationException(e.getMessage(), e);
                    });
        }
    }
}
