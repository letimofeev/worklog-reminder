package com.senla.worklog.reminder.employee.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents a data transfer object (DTO) for an employee domain model
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "Employee", description = "Representation of employee's domain object")
public class EmployeeDto implements Serializable {
    @Schema(description = "The unique identifier of the employee", example = "109")
    @Min(value = 1, message = "Employee id must be more than 0")
    private Long id;

    @Schema(description = "The first name of the employee", example = "Tomas")
    @Size(max = 255, message = "firstName must not be more than 255 symbols")
    private String firstName;

    @Schema(description = "The last name of the employee", example = "Shelby")
    @Size(max = 255, message = "lastName must not be more than 255 symbols")
    private String lastName;

    @Schema(description = "The jira key of the employee", example = "tomas_shelby")
    @Size(max = 64, message = "jiraKey must not be more than 64 symbols")
    private String jiraKey;

    @Schema(description = "The skype login of the employee", example = "live:skype-login322")
    @Size(max = 64, message = "skypeLogin must not be more than 64 symbols")
    private String skypeLogin;

    @Schema(description = "The region of the employee")
    private RegionDto region;

    @Schema(description = "The notification status of the employee")
    private NotificationStatusDto notificationStatus;

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", jiraKey='" + jiraKey + '\'' +
                ", skypeLogin='" + skypeLogin + '\'' +
                ", region=" + region +
                ", notificationStatus=" + notificationStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeDto that = (EmployeeDto) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(firstName, that.firstName)) return false;
        if (!Objects.equals(lastName, that.lastName)) return false;
        if (!Objects.equals(jiraKey, that.jiraKey)) return false;
        if (!Objects.equals(skypeLogin, that.skypeLogin)) return false;
        if (!Objects.equals(region, that.region)) return false;
        return Objects.equals(notificationStatus, that.notificationStatus);
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
        return result;
    }
}
