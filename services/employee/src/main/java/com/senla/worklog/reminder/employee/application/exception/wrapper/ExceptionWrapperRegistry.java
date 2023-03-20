package com.senla.worklog.reminder.employee.application.exception.wrapper;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
public class ExceptionWrapperRegistry {
    private final Map<Class<? extends Exception>, ExceptionWrapper> wrappersMap;

    public ExceptionWrapperRegistry(List<ExceptionWrapper> mappers) {
        this.wrappersMap = mappers.stream()
                .collect(toMap(ExceptionWrapper::getExceptionType, identity()));
    }

    public Optional<ExceptionWrapper> getMapper(Class<? extends Exception> exceptionType) {
        return Optional.ofNullable(wrappersMap.get(exceptionType));
    }
}
