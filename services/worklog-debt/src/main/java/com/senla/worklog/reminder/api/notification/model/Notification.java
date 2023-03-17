package com.senla.worklog.reminder.api.notification.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Notification {
    private String login;
    private String message;

    @Override
    public String toString() {
        return "Notification{" +
                "login='" + login + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notification that = (Notification) o;

        if (!Objects.equals(login, that.login)) return false;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
