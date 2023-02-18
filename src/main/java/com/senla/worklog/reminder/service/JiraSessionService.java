package com.senla.worklog.reminder.service;

import com.senla.worklog.reminder.model.JiraSession;

public interface JiraSessionService {
    JiraSession login();

    JiraSession getSession();
}
