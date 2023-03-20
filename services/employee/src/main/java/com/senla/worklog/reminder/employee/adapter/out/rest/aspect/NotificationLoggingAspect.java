package com.senla.worklog.reminder.employee.adapter.out.rest.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class NotificationLoggingAspect {

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
