package com.senla.worklog.reminder.employee.adapter.in.rest.dto;

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
public class ApiSubError {
    protected String message;

    @Override
    public String toString() {
        return "ApiSubError{" +
                "message='" + message + '\'' +
                '}';
    }
}