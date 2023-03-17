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
public class NotificationUser {
    private Long id;
    private String login;
    private String displayName;
    private Boolean enabled;

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

        NotificationUser user = (NotificationUser) o;

        if (!Objects.equals(id, user.id)) return false;
        if (!Objects.equals(login, user.login)) return false;
        if (!Objects.equals(displayName, user.displayName)) return false;
        return Objects.equals(enabled, user.enabled);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
        return result;
    }
}
