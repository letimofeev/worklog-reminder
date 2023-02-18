package com.senla.worklog.reminder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JiraSession {
    private String sessionId;

    public JiraSession(JiraSession session) {
        sessionId = session.getSessionId();
    }

    @Override
    public String toString() {
        return "JiraSession{" +
                "sessionId='" + sessionId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JiraSession session = (JiraSession) o;

        return Objects.equals(sessionId, session.sessionId);
    }

    @Override
    public int hashCode() {
        return sessionId != null ? sessionId.hashCode() : 0;
    }
}
