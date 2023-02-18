package com.senla.worklog.reminder.repository;

import com.senla.worklog.reminder.model.JiraSession;

import java.util.Optional;

public interface JiraSessionStorage {
    void addSession(JiraSession session);

    Optional<JiraSession> getSession();
}
