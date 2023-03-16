package com.senla.worklog.reminder.employee.adapter.out.rest.model;

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
public class NotificationUser {
    private Long id;
    private String login;
    private String displayName;
    private boolean enabled;

    @Override
    public String toString() {
        return "NotificationUser{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", displayName='" + displayName + '\'' +
                ", enabled=" + enabled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotificationUser that = (NotificationUser) o;

        if (enabled != that.enabled) return false;
        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(login, that.login)) return false;
        return Objects.equals(displayName, that.displayName);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (enabled ? 1 : 0);
        return result;
    }
}
