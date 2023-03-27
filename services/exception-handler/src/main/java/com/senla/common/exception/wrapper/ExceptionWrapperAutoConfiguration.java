package com.senla.common.exception.wrapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.List;

@Configuration
@EnableAspectJAutoProxy
public class ExceptionWrapperAutoConfiguration {
    @Bean
    public ExceptionWrapperRegistry exceptionWrapperRegistry(List<ExceptionWrapper> exceptionWrappers) {
        return new ExceptionWrapperRegistry(exceptionWrappers);
    }

    @Bean
    public ExceptionWrapperAspect exceptionWrapperAspect(ExceptionWrapperRegistry exceptionWrapperRegistry) {
        return new ExceptionWrapperAspect(exceptionWrapperRegistry);
    }
}
