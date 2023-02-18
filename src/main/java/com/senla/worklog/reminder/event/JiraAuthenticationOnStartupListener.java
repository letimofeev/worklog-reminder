package com.senla.worklog.reminder.event;

import com.senla.worklog.reminder.config.JiraProperties;
import com.senla.worklog.reminder.service.jira.JiraAuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JiraAuthenticationOnStartupListener implements ApplicationListener<ContextRefreshedEvent> {
    private final JiraAuthenticationService authenticationService;
    private final JiraProperties jiraProperties;

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        log.info("Login at the application startup");
        logLoginProperties();
        authenticationService.login();
    }

    private void logLoginProperties() {
        log.debug("Using Jira login properties:\n\t" +
                "jira.login-url                  " + jiraProperties.getLoginUrl() + "\n\t" +
                "jira.basic-auth.username        " + jiraProperties.getBasicAuth().getUsername() + "\n\t" +
                "jira.basic-auth.password        " + jiraProperties.getBasicAuth().getPassword() + "\n\t" +
                "jira.form-auth.username         " + jiraProperties.getFormAuth().getUsername() + "\n\t" +
                "jira.form-auth.password         " + jiraProperties.getFormAuth().getPassword() + "\n");
    }
}
