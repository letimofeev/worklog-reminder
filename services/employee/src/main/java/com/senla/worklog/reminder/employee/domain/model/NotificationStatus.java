package com.senla.worklog.reminder.employee.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationStatus {
    private boolean notificationEnabled;
    private boolean botConnected;

    public void adjustStatus() {
        if (!botConnected && notificationEnabled) {
            notificationEnabled = false;
        }
    }

    @Override
    public String toString() {
        return "NotificationStatus{" +
                "notificationEnabled=" + notificationEnabled +
                ", botConnected=" + botConnected +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotificationStatus that = (NotificationStatus) o;

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
