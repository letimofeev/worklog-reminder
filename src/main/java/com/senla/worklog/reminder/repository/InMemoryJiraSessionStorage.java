package com.senla.worklog.reminder.repository;

import com.senla.worklog.reminder.model.JiraSession;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InMemoryJiraSessionStorage implements JiraSessionStorage {
    private JiraSession jiraSession;

    @Override
    public void saveSession(JiraSession session) {
        jiraSession = new JiraSession(session);
    }

    @Override
    public Optional<JiraSession> getSession() {
        return Optional.ofNullable(jiraSession);
    }
}
