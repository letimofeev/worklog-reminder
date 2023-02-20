package com.senla.worklog.reminder.service.jira;

import com.senla.worklog.reminder.model.JiraSession;
import org.springframework.http.HttpHeaders;

public interface JiraAuthenticationService {
    JiraSession login();

    JiraSession getSession();

    HttpHeaders getAuthenticationHeaders();
}
