package com.senla.worklog.reminder.employee.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * This class represents a data transfer object (DTO) for creating a new employee
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "CreateEmployeeRequest", description = "Representation of add employee request body")
public class CreateEmployeeRequestDto implements Serializable {
    @Schema(description = "The first name of the employee", example = "Arthur")
    @NotBlank(message = "firstName must be specified")
    @Size(min = 1, max = 255, message = "firstName must not be more than 255 symbols or less that 1 symbol")
    private String firstName;

    @Schema(description = "The last name of the employee", example = "Morgan")
    @NotBlank(message = "lastName must be specified")
    @Size(min = 1, max = 255, message = "lastName must not be more than 255 symbols or less that 1 symbol")
    private String lastName;

    @Schema(description = "The jira key of the employee", example = "arthur_morgan")
    @NotBlank(message = "jiraKey must be specified")
    @Size(min = 1, max = 64, message = "jiraKey must not be more than 64 symbols or less that 1 symbol")
    private String jiraKey;

    @Schema(description = "The skype login of the employee", example = "skype-login123")
    @NotBlank(message = "skypeLogin must be specified")
    @Size(min = 1, max = 64, message = "skypeLogin must not be more than 64 symbols or less that 1 symbol")
    private String skypeLogin;

    @NotNull(message = "regionId must be specified")
    @Schema(description = "The region id of the employee", example = "ac7bc9b7-1515-4d57-a825-5001a83f2023")
    private UUID regionId;

    @Override
    public String toString() {
        return "CreateEmployeeDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", jiraKey='" + jiraKey + '\'' +
                ", skypeLogin='" + skypeLogin + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreateEmployeeRequestDto that = (CreateEmployeeRequestDto) o;

        if (!Objects.equals(firstName, that.firstName)) return false;
        if (!Objects.equals(lastName, that.lastName)) return false;
        if (!Objects.equals(jiraKey, that.jiraKey)) return false;
        return Objects.equals(skypeLogin, that.skypeLogin);
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (jiraKey != null ? jiraKey.hashCode() : 0);
        result = 31 * result + (skypeLogin != null ? skypeLogin.hashCode() : 0);
        return result;
    }
}
