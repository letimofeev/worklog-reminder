package com.senla.worklog.reminder.worklogdebt.adapter.out.rest.employee.dto;

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
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String jiraKey;
    private String skypeLogin;
    private boolean notificationEnabled;
    private boolean botConnected;
}
