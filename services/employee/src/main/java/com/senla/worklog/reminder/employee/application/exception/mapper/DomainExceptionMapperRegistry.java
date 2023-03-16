package com.senla.worklog.reminder.employee.application.exception.mapper;

import com.senla.worklog.reminder.employee.domain.exception.DomainException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
public class DomainExceptionMapperRegistry {
    private final Map<Class<? extends DomainException>, DomainExceptionMapper> mappersMap;

    public DomainExceptionMapperRegistry(List<DomainExceptionMapper> mappers) {
        this.mappersMap = mappers.stream()
                .collect(toMap(DomainExceptionMapper::getExceptionType, identity()));
    }

    public Optional<DomainExceptionMapper> getMapper(Class<? extends DomainException> exceptionType) {
        return Optional.ofNullable(mappersMap.get(exceptionType));
    }
}
