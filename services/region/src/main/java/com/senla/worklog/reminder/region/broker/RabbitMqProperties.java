package com.senla.worklog.reminder.region.broker;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "rabbitmq.region")
public class RabbitMqProperties {
    private String exchange;
    private QueueProperties queue;

    @Getter
    @Setter
    public static class QueueProperties {
        private String created;
        private String updated;
        private String deleted;
    }
}
