package com.senla.worklog.reminder.employee.application.aspect;

import com.senla.worklog.reminder.employee.application.exception.UnexpectedApplicationException;
import com.senla.worklog.reminder.employee.application.exception.wrapper.ExceptionWrapperRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ExceptionWrapperAspect {
    private final ExceptionWrapperRegistry wrapperRegistry;

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
                        log.warn("ApplicationExceptionWrapper not found for exception: {}", e.getClass());
                        return new UnexpectedApplicationException(e.getMessage(), e);
                    });
        }
    }
}
