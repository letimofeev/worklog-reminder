package com.senla.worklog.reminder.worklogdebt.adapter.out.rest.vacation;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "vacation.rest.api")
public class VacationRestProperties {
    private String baseUrl;
    private String getVacationsUri;
}
