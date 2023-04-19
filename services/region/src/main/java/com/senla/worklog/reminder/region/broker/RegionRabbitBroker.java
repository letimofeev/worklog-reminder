package com.senla.worklog.reminder.region.broker;

import com.senla.worklog.reminder.region.event.RegionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static org.springframework.amqp.core.MessageProperties.CONTENT_TYPE_JSON;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegionRabbitBroker implements RegionBroker {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitProperties properties;

    @Override
    public void sendRegionEvent(RegionEvent event) {
        var routingKey = event.getEventType().toString();
        var exchange = properties.getExchange();
        log.debug("Sending event {} to the RabbitMQ exchange '{}'", event, exchange);

        rabbitTemplate.convertAndSend(exchange, routingKey, event, message -> {
            var messageProperties = message.getMessageProperties();
            messageProperties.setContentType(CONTENT_TYPE_JSON);
            return message;
        });
    }
}
