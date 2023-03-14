package com.senla.worklog.reminder.api.notification.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum NotificationStatus {
    @JsonProperty("pass")
    PASS,

    @JsonProperty("failed")
    FAILED
}
