package com.senla.worklog.reminder.employee.adapter.in.rest.exception;

import com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler.RestAdapterExceptionHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
public class RestAdapterExceptionHandlerRegistry {
    private final Map<Class<? extends Throwable>, RestAdapterExceptionHandler> handlersMap;

    public RestAdapterExceptionHandlerRegistry(List<RestAdapterExceptionHandler> handlers) {
        this.handlersMap = handlers.stream()
                .collect(toMap(RestAdapterExceptionHandler::getExceptionType, identity()));
    }

    public Optional<RestAdapterExceptionHandler> getHandler(Class<? extends Throwable> exceptionType) {
        return Optional.ofNullable(handlersMap.get(exceptionType));
    }
}
