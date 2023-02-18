package com.senla.worklog.reminder.event;

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

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        log.info("Login at the application startup");
        authenticationService.login();
    }
}
