package com.senla.worklog.reminder.region.broker;

import com.senla.worklog.reminder.region.event.RegionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegionRabbitMq implements RegionBroker {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMqProperties properties;

    @Override
    public void sendRegionEvent(RegionEvent event) {
        var routingKey = event.getEventType().toString();
        var exchange = properties.getExchange();
        log.debug("Sending event with routing key {} to the RabbitMQ exchange {}", routingKey, exchange);

        rabbitTemplate.convertAndSend(exchange, routingKey, event, message -> {
            var messageProperties = message.getMessageProperties();
            messageProperties.setContentType("application/json");
            return message;
        });
    }
}
