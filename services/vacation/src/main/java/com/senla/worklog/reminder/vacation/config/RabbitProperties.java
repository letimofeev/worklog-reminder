package com.senla.worklog.reminder.vacation.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "rabbitmq.region")
public class RabbitProperties {
    private String exchange;
    private String exchangeDlq;
    private QueueProperties queue;

    @Getter
    @Setter
    public static class QueueProperties {
        private String created;
        private String updated;
        private String deleted;
    }
}
