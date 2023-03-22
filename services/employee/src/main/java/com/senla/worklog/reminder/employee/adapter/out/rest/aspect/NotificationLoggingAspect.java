package com.senla.worklog.reminder.employee.adapter.out.rest.aspect;

import com.senla.worklog.reminder.employee.adapter.out.rest.NotificationRestAdapter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging the execution time of methods in {@link NotificationRestAdapter}
 */
@Slf4j
@Aspect
@Component
public class NotificationLoggingAspect {

    /**
     * Logs the execution time of methods in {@link NotificationRestAdapter}
     *
     * @param joinPoint the join point of the intercepted method
     * @return the result of the intercepted method
     * @throws Throwable if the intercepted method throws an exception
     */
    @Around("execution(* com.senla.worklog.reminder.employee.adapter.out.rest.NotificationRestAdapter.*(..))")
    public Object aroundNotificationAdapterMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        var name = joinPoint.getSignature().getName();
        log.trace("Executing notification rest adapter method: {}", name);

        long begin = System.currentTimeMillis();
        var result = joinPoint.proceed();
        long timeElapsed = System.currentTimeMillis() - begin;

        log.trace("Executed notification rest adapter method: {}. Time elapsed: {} ms", name, timeElapsed);
        return result;
    }
}
