package com.senla.worklog.reminder.employee.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EmployeeRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String jiraKey;
    private String skypeLogin;

    @Override
    public String toString() {
        return "EmployeeRequest{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", jiraKey='" + jiraKey + '\'' +
                ", skypeLogin='" + skypeLogin + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeRequest that = (EmployeeRequest) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(firstName, that.firstName)) return false;
        if (!Objects.equals(lastName, that.lastName)) return false;
        if (!Objects.equals(jiraKey, that.jiraKey)) return false;
        return Objects.equals(skypeLogin, that.skypeLogin);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (jiraKey != null ? jiraKey.hashCode() : 0);
        result = 31 * result + (skypeLogin != null ? skypeLogin.hashCode() : 0);
        return result;
    }
}
