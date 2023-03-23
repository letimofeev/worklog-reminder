package com.senla.worklog.reminder.worklogdebt.adapter.in.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiSubError {
    protected String message;

    @Override
    public String toString() {
        return "ApiSubError{" +
                "message='" + message + '\'' +
                '}';
    }
}
