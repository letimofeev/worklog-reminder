package com.senla.worklog.reminder.worklogdebt.adapter.in.rest.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.SpringDocConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * The SwaggerConfiguration class is responsible for configuring and initializing the Swagger API documentation
 * framework for the application. It is annotated with {@link Configuration} to indicate that it provides Spring
 * configuration, and it contains various methods annotated with {@link Bean} that provide the necessary beans
 * for Swagger to function
 */
@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("WorklogDebt Service")
                        .description("API for retrieving employees worklog debts")
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
