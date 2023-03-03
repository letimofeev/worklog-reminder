package com.senla.worklog.reminder.service.jira;

import com.senla.worklog.reminder.model.JiraSession;
import org.springframework.http.ResponseEntity;

public interface LoginResponseParser {
    JiraSession parseLoginResponse(ResponseEntity<String> response);
}
