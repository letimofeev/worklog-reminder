package com.senla.worklog.reminder.employee.application.exception.wrapper;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * A registry that holds a map of {@link ExceptionWrapper} instances mapped to their corresponding exception types.
 * Provides a method to get the wrapper instance for a given exception type
 */
@Component
public class ExceptionWrapperRegistry {
    private final Map<Class<? extends Exception>, ExceptionWrapper> wrappersMap;

    /**
     * Constructs a new {@link ExceptionWrapperRegistry} instance with a list of {@link ExceptionWrapper}s.
     * The list is converted into a map with exception types as keys and their corresponding wrappers as values
     *
     * @param mappers a list of {@link ExceptionWrapper}s to be registered in the registry
     */
    public ExceptionWrapperRegistry(List<ExceptionWrapper> mappers) {
        this.wrappersMap = mappers.stream()
                .collect(toMap(ExceptionWrapper::getExceptionType, identity()));
    }

    /**
     * Returns an {@link Optional} of the {@link ExceptionWrapper} instance corresponding to the given exception type.
     * If no wrapper is found for the given type, an empty {@link Optional} is returned
     *
     * @param exceptionType the type of the exception for which to get the wrapper
     * @return an {@link Optional} of the corresponding {@link ExceptionWrapper} instance,
     * or an empty optional if no wrapper is found
     */
    public Optional<ExceptionWrapper> getMapper(Class<? extends Exception> exceptionType) {
        return Optional.ofNullable(wrappersMap.get(exceptionType));
    }
}
