package com.senla.worklog.reminder.worklogdebt.adapter.out.rest.jira.auth;

import com.senla.worklog.reminder.worklogdebt.adapter.out.rest.jira.auth.session.JiraSession;
import org.springframework.http.ResponseEntity;

public interface LoginResponseParser {
    JiraSession parseLoginResponse(ResponseEntity<String> response);
}
