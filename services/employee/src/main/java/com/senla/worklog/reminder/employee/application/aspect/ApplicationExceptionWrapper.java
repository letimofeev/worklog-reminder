package com.senla.worklog.reminder.employee.application.aspect;

import com.senla.worklog.reminder.employee.application.exception.UnexpectedApplicationException;
import com.senla.worklog.reminder.employee.application.exception.UnmappedApplicationException;
import com.senla.worklog.reminder.employee.application.exception.mapper.DomainExceptionMapperRegistry;
import com.senla.worklog.reminder.employee.domain.exception.DomainException;
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
public class ApplicationExceptionWrapper {
    private final DomainExceptionMapperRegistry mapperRegistry;

    @Around("@within(com.senla.worklog.reminder.employee.application.annotation.WrappedInApplicationException)")
    public Object wrapMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (DomainException e) {
            log.debug("Caught domain exception, trying to find mapping ApplicationException to wrap: {}", e.getClass());
            throw mapperRegistry.getMapper(e.getClass())
                    .map(mapper -> {
                        log.debug("Found mapper '{}' for domain exception '{}'",
                                mapper.getClass().getSimpleName(), e.getClass().getSimpleName());
                        return mapper.mapToApplicationException(e);
                    })
                    .orElseGet(() -> {
                        log.warn("Mapper to ApplicationException not found for domain exception: {}", e.getClass());
                        return new UnmappedApplicationException(e.getMessage(), e, e.detailMessages());
                    });
        } catch (Exception e) {
            throw new UnexpectedApplicationException(e);
        }
    }
}
