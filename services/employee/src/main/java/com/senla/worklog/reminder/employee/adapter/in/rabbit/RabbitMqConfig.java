package com.senla.worklog.reminder.employee.adapter.in.rabbit;

import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.senla.worklog.reminder.employee.adapter.in.rabbit.event.RegionEventType.REGION_CREATED;
import static com.senla.worklog.reminder.employee.adapter.in.rabbit.event.RegionEventType.REGION_DELETED;
import static com.senla.worklog.reminder.employee.adapter.in.rabbit.event.RegionEventType.REGION_UPDATED;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "rabbitmq.region")
public class RabbitMqConfig {
    private String exchange;
    private QueueProperties queue;

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
}
