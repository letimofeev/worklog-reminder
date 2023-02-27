package com.senla.worklog.reminder.aspect;

import com.senla.worklog.reminder.annotation.RefreshableSession;
import com.senla.worklog.reminder.exception.JiraAuthenticationException;
import com.senla.worklog.reminder.service.jira.JiraAuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RefreshableSessionAspect {
    private final JiraAuthenticationService authenticationService;

    @Around("@annotation(com.senla.worklog.reminder.annotation.RefreshableSession)")
    public Object aroundRefreshableAnnotatedMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        var methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        int maxRetries = methodSignature.getMethod().getAnnotation(RefreshableSession.class).maxRetries();
        int currentRetries = 0;
        while (currentRetries <= maxRetries) {
            try {
                return proceedingJoinPoint.proceed();
            } catch (Exception e) {
                currentRetries = handleException(maxRetries, currentRetries, e);
            }
        }
        log.error("Received 401 Unauthorized after retries with login. Total attempts number: {}", currentRetries);
        throw new JiraAuthenticationException("Jira Api Client 401 Unauthorized after retry with login. " +
                "Make sure that request was sent with correct headers");
    }

    private int handleException(int maxRetries, int currentRetries, Exception e) throws Exception {
        if (isExceptionOrCauseUnauthorized(e)) {
            if (currentRetries < maxRetries) {
                log.warn("Received 401 Unauthorized. Perhaps session is expired, logging in and trying again. " +
                                "Retry number: {}/{}", currentRetries, maxRetries);
                authenticationService.login();
            }
            currentRetries++;
        } else {
            throw e;
        }
        return currentRetries;
    }

    private boolean isExceptionOrCauseUnauthorized(Exception e) {
        return e instanceof Unauthorized || e.getCause() instanceof Unauthorized;
    }
}
