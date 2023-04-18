package com.senla.worklog.reminder.vacation.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.senla.worklog.reminder.vacation.event.RegionEventType.REGION_CREATED;
import static com.senla.worklog.reminder.vacation.event.RegionEventType.REGION_DELETED;
import static com.senla.worklog.reminder.vacation.event.RegionEventType.REGION_UPDATED;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RabbitConfig {
    private final RabbitProperties properties;

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public DirectExchange regionExchange() {
        return new DirectExchange(properties.getExchange());
    }

    @Bean
    public DirectExchange regionExchangeDlq() {
        return new DirectExchange(properties.getExchangeDlq());
    }

    @Bean
    public Queue regionCreatedQueue() {
        var queueName = properties.getQueue().getCreated();
        return QueueBuilder.durable(queueName)
                .withArgument("x-dead-letter-exchange", properties.getExchangeDlq())
                .withArgument("x-dead-letter-routing-key", getDlqRoutingKey(REGION_CREATED))
                .build();
    }

    @Bean
    public Queue regionUpdatedQueue() {
        var queueName = properties.getQueue().getUpdated();
        return QueueBuilder.durable(queueName)
                .withArgument("x-dead-letter-exchange", properties.getExchangeDlq())
                .withArgument("x-dead-letter-routing-key", getDlqRoutingKey(REGION_UPDATED))
                .build();
    }

    @Bean
    public Queue regionDeletedQueue() {
        var queueName = properties.getQueue().getDeleted();
        return QueueBuilder.durable(queueName)
                .withArgument("x-dead-letter-exchange", properties.getExchangeDlq())
                .withArgument("x-dead-letter-routing-key", getDlqRoutingKey(REGION_DELETED))
                .build();
    }

    @Bean
    public Queue regionCreatedDlq() {
        var queueName = getDlqName(properties.getQueue().getCreated());
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Queue regionUpdatedDlq() {
        var queueName = getDlqName(properties.getQueue().getUpdated());
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Queue regionDeletedDlq() {
        var queueName = getDlqName(properties.getQueue().getDeleted());
        return QueueBuilder.durable(queueName).build();
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
    public Binding regionCreatedDlqBinding() {
        return BindingBuilder.bind(regionCreatedDlq())
                .to(regionExchangeDlq())
                .with(getDlqRoutingKey(REGION_CREATED));
    }

    @Bean
    public Binding regionUpdatedDlqBinding() {
        return BindingBuilder.bind(regionUpdatedDlq())
                .to(regionExchangeDlq())
                .with(getDlqRoutingKey(REGION_UPDATED));
    }

    @Bean
    public Binding regionDeletedDlqBinding() {
        return BindingBuilder.bind(regionDeletedDlq())
                .to(regionExchangeDlq())
                .with(getDlqRoutingKey(REGION_DELETED));
    }

    private String getDlqRoutingKey(Enum<?> event) {
        return event.toString() + "_dlq";
    }

    private String getDlqName(String queueName) {
        return queueName + ".dlq";
    }
}
