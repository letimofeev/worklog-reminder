package com.senla.worklog.reminder.worklogdebt.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class EmployeeWorklogDebtsDto {
    @Schema(description = "The unique identifier of the employee", example = "109")
    private Long id;

    @Schema(description = "The first name of the employee", example = "Tomas")
    private String firstName;

    @Schema(description = "The last name of the employee", example = "Shelby")
    private String lastName;

    @Schema(description = "The jira key of the employee", example = "tomas_shelby")
    private String jiraKey;

    @Schema(description = "The skype login of the employee", example = "live:skype-login322")
    private String skypeLogin;

    @Schema(description = "The region of the employee")
    private RegionDto region;

    @Schema(description = "The notification status of the employee")
    private NotificationStatusDto notificationStatus;

    @Schema(description = "Date from in ISO format", example = "2023-03-01")
    private LocalDate dateFrom;

    @Schema(description = "Date to in ISO format", example = "2023-03-03")
    private LocalDate dateTo;

    @Schema(description = "Count of employee's worklog debts", example = "1")
    private Integer worklogDebtsCount;

    @Schema(description = "Employee worklog debts")
    private List<DayWorklogDebtDto> worklogDebts;

    @Schema(description = "Excluded days from date range (for example employee's vacation days)")
    private List<ExcludedDayDto> excludedDays;

    @Override
    public String toString() {
        return "EmployeeWorklogDebtsDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", jiraKey='" + jiraKey + '\'' +
                ", skypeLogin='" + skypeLogin + '\'' +
                ", region=" + region +
                ", notificationStatus=" + notificationStatus +
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

        EmployeeWorklogDebtsDto that = (EmployeeWorklogDebtsDto) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(firstName, that.firstName)) return false;
        if (!Objects.equals(lastName, that.lastName)) return false;
        if (!Objects.equals(jiraKey, that.jiraKey)) return false;
        if (!Objects.equals(skypeLogin, that.skypeLogin)) return false;
        if (!Objects.equals(region, that.region)) return false;
        if (!Objects.equals(notificationStatus, that.notificationStatus))
            return false;
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
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (notificationStatus != null ? notificationStatus.hashCode() : 0);
        result = 31 * result + (dateFrom != null ? dateFrom.hashCode() : 0);
        result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
        result = 31 * result + (worklogDebtsCount != null ? worklogDebtsCount.hashCode() : 0);
        result = 31 * result + (worklogDebts != null ? worklogDebts.hashCode() : 0);
        result = 31 * result + (excludedDays != null ? excludedDays.hashCode() : 0);
        return result;
    }
}
