package com.senla.worklog.reminder.worklogdebt.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EmployeeWorklogDebts {
    private Long id;
    private String firstName;
    private String lastName;
    private String jiraKey;
    private String skypeLogin;
    private NotificationStatus notificationStatus;
    private Region region;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Integer worklogDebtsCount;
    private List<DayWorklogDebt> worklogDebts;
    private List<ExcludedDay> excludedDays;

    @Override
    public String toString() {
        return "EmployeeWorklogDebts{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", jiraKey='" + jiraKey + '\'' +
                ", skypeLogin='" + skypeLogin + '\'' +
                ", notificationStatus=" + notificationStatus +
                ", region=" + region +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", worklogDebtsCount=" + worklogDebtsCount +
                ", worklogDebts=" + worklogDebts +
                ", excludedDays=" + excludedDays +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeWorklogDebts that = (EmployeeWorklogDebts) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(firstName, that.firstName)) return false;
        if (!Objects.equals(lastName, that.lastName)) return false;
        if (!Objects.equals(jiraKey, that.jiraKey)) return false;
        if (!Objects.equals(skypeLogin, that.skypeLogin)) return false;
        if (!Objects.equals(notificationStatus, that.notificationStatus))
            return false;
        if (!Objects.equals(region, that.region)) return false;
        if (!Objects.equals(dateFrom, that.dateFrom)) return false;
        if (!Objects.equals(dateTo, that.dateTo)) return false;
        if (!Objects.equals(worklogDebtsCount, that.worklogDebtsCount))
            return false;
        if (!Objects.equals(worklogDebts, that.worklogDebts)) return false;
        return Objects.equals(excludedDays, that.excludedDays);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (jiraKey != null ? jiraKey.hashCode() : 0);
        result = 31 * result + (skypeLogin != null ? skypeLogin.hashCode() : 0);
        result = 31 * result + (notificationStatus != null ? notificationStatus.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (dateFrom != null ? dateFrom.hashCode() : 0);
        result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
        result = 31 * result + (worklogDebtsCount != null ? worklogDebtsCount.hashCode() : 0);
        result = 31 * result + (worklogDebts != null ? worklogDebts.hashCode() : 0);
        result = 31 * result + (excludedDays != null ? excludedDays.hashCode() : 0);
        return result;
    }
}
