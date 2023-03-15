package com.senla.worklog.reminder.employee.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EmployeeResponse implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String jiraKey;
    private String skypeLogin;
    private Boolean notificationEnabled;
    private Boolean botConnected;

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", jiraKey='" + jiraKey + '\'' +
                ", skypeLogin='" + skypeLogin + '\'' +
                ", notificationEnabled=" + notificationEnabled +
                ", botConnected=" + botConnected +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeResponse that = (EmployeeResponse) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(firstName, that.firstName)) return false;
        if (!Objects.equals(lastName, that.lastName)) return false;
        if (!Objects.equals(jiraKey, that.jiraKey)) return false;
        if (!Objects.equals(skypeLogin, that.skypeLogin)) return false;
        if (!Objects.equals(notificationEnabled, that.notificationEnabled))
            return false;
        return Objects.equals(botConnected, that.botConnected);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (jiraKey != null ? jiraKey.hashCode() : 0);
        result = 31 * result + (skypeLogin != null ? skypeLogin.hashCode() : 0);
        result = 31 * result + (notificationEnabled != null ? notificationEnabled.hashCode() : 0);
        result = 31 * result + (botConnected != null ? botConnected.hashCode() : 0);
        return result;
    }
}
