package com.senla.worklog.reminder.worklogdebtnotification.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EmployeeWorklogDebts {
    private String firstName;
    private String lastName;
    private String skypeLogin;
    private List<DayWorklogDebt> worklogDebts;

    @Override
    public String toString() {
        return "EmployeeWorklogDebts{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", skypeLogin='" + skypeLogin + '\'' +
                ", worklogDebts=" + worklogDebts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeWorklogDebts that = (EmployeeWorklogDebts) o;

        if (!Objects.equals(firstName, that.firstName)) return false;
        if (!Objects.equals(lastName, that.lastName)) return false;
        if (!Objects.equals(skypeLogin, that.skypeLogin)) return false;
        return Objects.equals(worklogDebts, that.worklogDebts);
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (skypeLogin != null ? skypeLogin.hashCode() : 0);
        result = 31 * result + (worklogDebts != null ? worklogDebts.hashCode() : 0);
        return result;
    }
}
