package com.senla.worklog.reminder.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

/**
 * This is the main class of the Employee Application. It is responsible for bootstrapping the application
 * and starting the Spring Boot framework.
 */
@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class EmployeeApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}
}
