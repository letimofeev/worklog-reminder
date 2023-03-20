package com.senla.worklog.reminder.employee.application.exception.mapper;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
public class ApplicationExceptionWrapperRegistry {
    private final Map<Class<? extends Exception>, ApplicationExceptionWrapper> wrappersMap;

    public ApplicationExceptionWrapperRegistry(List<ApplicationExceptionWrapper> mappers) {
        this.wrappersMap = mappers.stream()
                .collect(toMap(ApplicationExceptionWrapper::getExceptionType, identity()));
    }

    public Optional<ApplicationExceptionWrapper> getMapper(Class<? extends Exception> exceptionType) {
        return Optional.ofNullable(wrappersMap.get(exceptionType));
    }
}
