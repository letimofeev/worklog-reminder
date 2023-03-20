package com.senla.worklog.reminder.employee.adapter.in.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

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

    public static ApiError internalServerError() {
        return new ApiError("Internal server error", INTERNAL_SERVER_ERROR.value());
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
