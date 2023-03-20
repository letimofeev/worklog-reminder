package com.senla.worklog.reminder.employee.adapter.out.rest;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import static java.time.Duration.ofSeconds;

@Getter
@Setter
@Configuration
@ConfigurationProperties("rest-template")
public class RestTemplateConfig {
    private int readTimeoutSeconds;
    private int connectionTimeoutSeconds;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .requestFactory(HttpComponentsClientHttpRequestFactory.class)
                .setConnectTimeout(ofSeconds(readTimeoutSeconds))
                .setReadTimeout(ofSeconds(connectionTimeoutSeconds))
                .build();
    }
}
