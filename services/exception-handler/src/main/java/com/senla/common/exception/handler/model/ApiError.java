package com.senla.common.exception.handler.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

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

    public static ApiError notFound(String message) {
        return new ApiError(message, NOT_FOUND.value());
    }

    public static ApiError methodNotAllowed(String message) {
        return new ApiError(message, METHOD_NOT_ALLOWED.value());
    }

    public static ApiError unsupportedMediaType(String message) {
        return new ApiError(message, UNSUPPORTED_MEDIA_TYPE.value());
    }

    public static ApiError notAcceptable(String message) {
        return new ApiError(message, NOT_ACCEPTABLE.value());
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
