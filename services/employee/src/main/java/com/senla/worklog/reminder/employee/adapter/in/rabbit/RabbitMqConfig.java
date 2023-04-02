package com.senla.worklog.reminder.employee.adapter.in.rabbit;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.retry.listener.RetryListenerSupport;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import static com.senla.worklog.reminder.employee.adapter.in.rabbit.event.RegionEventType.REGION_CREATED;
import static com.senla.worklog.reminder.employee.adapter.in.rabbit.event.RegionEventType.REGION_DELETED;
import static com.senla.worklog.reminder.employee.adapter.in.rabbit.event.RegionEventType.REGION_UPDATED;

@Slf4j
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "rabbitmq.region")
public class RabbitMqConfig {
    private String exchange;
    private QueueProperties queue;
    private Integer maxRetries;
    private Integer backoffMultiplier;
    private Long initialBackoffMs;
    private Long maxBackoffMs;

    @Getter
    @Setter
    public static class QueueProperties {
        private String created;
        private String updated;
        private String deleted;
    }

    @Bean
    public Queue regionCreatedQueue() {
        return new Queue(queue.getCreated(), true);
    }

    @Bean
    public Queue regionUpdatedQueue() {
        return new Queue(queue.getUpdated(), true);
    }

    @Bean
    public Queue regionDeletedQueue() {
        return new Queue(queue.getDeleted(), true);
    }

    @Bean
    public TopicExchange regionExchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding regionCreatedBinding() {
        return BindingBuilder.bind(regionCreatedQueue())
                .to(regionExchange())
                .with(REGION_CREATED);
    }

    @Bean
    public Binding regionUpdatedBinding() {
        return BindingBuilder.bind(regionUpdatedQueue())
                .to(regionExchange())
                .with(REGION_UPDATED);
    }

    @Bean
    public Binding regionDeletedBinding() {
        return BindingBuilder.bind(regionDeletedQueue())
                .to(regionExchange())
                .with(REGION_DELETED);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory, SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        var factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setAdviceChain(retryAdvice());
        return factory;
    }

    @Bean
    public RetryOperationsInterceptor retryAdvice() {
        var retryTemplate = new RetryTemplate();
        var retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(maxRetries);
        retryTemplate.setRetryPolicy(retryPolicy);

        var backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(initialBackoffMs);
        backOffPolicy.setMultiplier(backoffMultiplier);
        backOffPolicy.setMaxInterval(maxBackoffMs);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        retryTemplate.registerListener(new RetryListenerSupport() {
            @Override
            public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
                log.debug("Error during processing message, retrying. Retry count: {}", context.getRetryCount());
            }
        });

        return RetryInterceptorBuilder.stateless()
                .retryOperations(retryTemplate)
                .build();
    }
}
