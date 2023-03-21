package com.senla.worklog.reminder.employee.adapter.in.rest;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.SpringDocConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Employee Service")
                        .description("API for managing employees")
                        .version("0.0.1"));
    }

    @Bean
    public SpringDocConfigProperties springDocConfigProperties() {
        SpringDocConfigProperties properties = new SpringDocConfigProperties();
        properties.setDefaultConsumesMediaType(APPLICATION_JSON_VALUE);
        properties.setDefaultProducesMediaType(APPLICATION_JSON_VALUE);
        return properties;
    }
}
