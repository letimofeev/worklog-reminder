package com.senla.worklog.reminder.employee.domain.model;

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
public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private String jiraKey;
    private String skypeLogin;
    private Boolean notificationEnabled;
    private Boolean botConnected;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (!Objects.equals(id, employee.id)) return false;
        if (!Objects.equals(firstName, employee.firstName)) return false;
        if (!Objects.equals(lastName, employee.lastName)) return false;
        if (!Objects.equals(jiraKey, employee.jiraKey)) return false;
        if (!Objects.equals(skypeLogin, employee.skypeLogin)) return false;
        if (!Objects.equals(notificationEnabled, employee.notificationEnabled))
            return false;
        return Objects.equals(botConnected, employee.botConnected);
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

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", jiraKey='" + jiraKey + '\'' +
                ", skypeLogin='" + skypeLogin + '\'' +
                ", notificationEnabled=" + notificationEnabled +
                ", botConnected=" + botConnected +
                '}';
    }
}
