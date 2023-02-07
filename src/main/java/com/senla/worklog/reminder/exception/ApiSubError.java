package com.senla.worklog.reminder.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiSubError {
    private String details;

    @Override
    public String toString() {
        return "ApiSubError{" +
                "details='" + details + '\'' +
                '}';
    }
}
