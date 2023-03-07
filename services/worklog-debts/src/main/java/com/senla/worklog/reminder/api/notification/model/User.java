package com.senla.worklog.reminder.api.notification.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class User {
    private Long id;
    private String login;
    private String displayName;
    private Boolean enabled;
}
