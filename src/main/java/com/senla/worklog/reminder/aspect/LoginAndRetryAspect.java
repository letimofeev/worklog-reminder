package com.senla.worklog.reminder.aspect;

import com.senla.worklog.reminder.annotation.LoginAndRetry;
import com.senla.worklog.reminder.exception.JiraAuthenticationException;
import com.senla.worklog.reminder.service.jira.JiraAuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoginAndRetryAspect {
    private final JiraAuthenticationService authenticationService;

    @Around("@annotation(com.senla.worklog.reminder.annotation.LoginAndRetry)")
    public Object aroundLoginAndRetryAnnotatedMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        int maxRetries = methodSignature.getMethod().getAnnotation(LoginAndRetry.class).maxRetries();
        int currentRetries = 0;
        while (currentRetries <= maxRetries) {
            try {
                return proceedingJoinPoint.proceed();
            } catch (Exception e) {
                if (e instanceof HttpClientErrorException.Unauthorized || e.getCause() instanceof HttpClientErrorException.Unauthorized) {
                    if (currentRetries < maxRetries) {
                        log.warn("Received 401 Unauthorized. Perhaps session is expired, logging in and trying again. Retry number: {}/{}",
                                currentRetries, maxRetries);
                        authenticationService.login();
                    }
                    currentRetries++;
                } else {
                    throw e;
                }
            }
        }
        log.error("Received 401 Unauthorized after retries with login. Total attempts number: {}", currentRetries);
        throw new JiraAuthenticationException("Jira Api Client 401 Unauthorized after retry with login. " +
                "Make sure that request was sent with correct headers");
    }
}
