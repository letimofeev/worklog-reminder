package com.senla.worklog.reminder.employee.adapter.in.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private String message;
    private Integer status;
    private List<ApiSubError> errors;

    public ApiError(String message, Integer status) {
        this.message = message;
        this.status = status;
        this.errors = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", errors=" + errors +
                '}';
    }
}
