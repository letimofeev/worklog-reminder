package com.senla.worklog.reminder.worklogdebt.adapter.out.rest.employee;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "employee.rest.api")
public class EmployeeRestProperties {
    private String baseUrl;
    private String getEmployeeByJiraKeyUri;
}
