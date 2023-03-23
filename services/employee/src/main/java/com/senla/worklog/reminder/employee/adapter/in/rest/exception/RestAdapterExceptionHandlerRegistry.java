package com.senla.worklog.reminder.employee.adapter.in.rest.exception;

import com.senla.worklog.reminder.employee.adapter.in.rest.exception.handler.RestAdapterExceptionHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * A registry for mapping exceptions to their corresponding {@link RestAdapterExceptionHandler} instances
 */
@Component
public class RestAdapterExceptionHandlerRegistry {
    private final Map<Class<? extends Throwable>, RestAdapterExceptionHandler> handlersMap;

    /**
     * Constructs a new {@link RestAdapterExceptionHandlerRegistry} instance with the given list of exception handlers.
     * Each handler is added to the handlers map using its {@link RestAdapterExceptionHandler#getExceptionType()} method
     * as the key and the handler instance itself as the value
     *
     * @param handlers a list of exception handlers
     */
    public RestAdapterExceptionHandlerRegistry(List<RestAdapterExceptionHandler> handlers) {
        this.handlersMap = handlers.stream()
                .collect(toMap(RestAdapterExceptionHandler::getExceptionType, identity()));
    }

    /**
     * Returns an {@link Optional} containing the {@link RestAdapterExceptionHandler} instance associated with the given
     * exception type, or an empty {@link Optional} if no such handler is registered
     *
     * @param exceptionType the exception type to find a handler for
     * @return an {@link Optional} containing the {@link RestAdapterExceptionHandler} instance associated with the given
     * exception type, or an empty {@link Optional} if no such handler is registered
     */
    public Optional<RestAdapterExceptionHandler> getHandler(Class<? extends Throwable> exceptionType) {
        return Optional.ofNullable(handlersMap.get(exceptionType));
    }
}
