package com.senla.worklog.reminder.employee.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "NotificationStatus", description = "Representation of employee's notification status value object")
public class NotificationStatusDto {
    @Schema(description = "Whether notifications are enabled for the employee", example = "false")
    private boolean notificationEnabled;

    @Schema(description = "Whether employee is connected to the notification service", example = "true")
    private boolean botConnected;

    @Override
    public String toString() {
        return "NotificationStatusDto{" +
                "notificationEnabled=" + notificationEnabled +
                ", botConnected=" + botConnected +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotificationStatusDto that = (NotificationStatusDto) o;

        if (notificationEnabled != that.notificationEnabled) return false;
        return botConnected == that.botConnected;
    }

    @Override
    public int hashCode() {
        int result = (notificationEnabled ? 1 : 0);
        result = 31 * result + (botConnected ? 1 : 0);
        return result;
    }
}
