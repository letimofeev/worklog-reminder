package com.senla.worklog.reminder.worklogdebt.adapter.out.rest.jira.auth.session;

import java.util.Optional;

public interface JiraSessionStorage {
    void saveSession(JiraSession session);

    Optional<JiraSession> getSession();
}
