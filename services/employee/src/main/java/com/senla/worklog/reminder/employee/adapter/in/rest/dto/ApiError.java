package com.senla.worklog.reminder.employee.adapter.in.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

/**
 * This class represents an error response returned by the API. It contains a message, status code, and a list of
 * sub-errors.
 */
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

    public static ApiError badRequest(String message, List<ApiSubError> subErrors) {
        return new ApiError(message, BAD_REQUEST.value(), subErrors);
    }

    public static ApiError badRequest(String message) {
        return new ApiError(message, BAD_REQUEST.value());
    }

    public static ApiError notFound(String message, List<ApiSubError> subErrors) {
        return new ApiError(message, NOT_FOUND.value(), subErrors);
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
