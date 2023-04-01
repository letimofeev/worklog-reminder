package com.senla.worklog.reminder.region.broker;

import com.senla.worklog.reminder.region.event.RegionEventType;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMqConfig {
    private final RabbitMqProperties properties;

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue regionCreatedQueue() {
        return new Queue(properties.getQueue().getCreated(), true);
    }

    @Bean
    public Queue regionUpdatedQueue() {
        return new Queue(properties.getQueue().getUpdated(), true);
    }

    @Bean
    public Queue regionDeletedQueue() {
        return new Queue(properties.getQueue().getDeleted(), true);
    }

    @Bean
    public TopicExchange regionExchange() {
        return new TopicExchange(properties.getExchange());
    }

    @Bean
    public Binding regionCreatedBinding() {
        return BindingBuilder.bind(regionCreatedQueue())
                .to(regionExchange())
                .with(RegionEventType.REGION_CREATED);
    }

    @Bean
    public Binding regionUpdatedBinding() {
        return BindingBuilder.bind(regionUpdatedQueue())
                .to(regionExchange())
                .with(RegionEventType.REGION_UPDATED);
    }

    @Bean
    public Binding regionDeletedBinding() {
        return BindingBuilder.bind(regionDeletedQueue())
                .to(regionExchange())
                .with(RegionEventType.REGION_DELETED);
    }
}
