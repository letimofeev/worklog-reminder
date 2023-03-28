package com.senla.worklog.reminder.apigateway.error;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
public class GatewayErrorAttributes extends DefaultErrorAttributes {
    public static final String ROUTE_DEFINITION_NOT_FOUND_ERR = "No route definition with path '%s' found";
    public static final String INVALID_STATUS_CODE_ERR = "No HttpStatus corresponds to status code %d";

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        var error = super.getError(request);
        var originalErrorAttributes = super.getErrorAttributes(request, options);


        int statusCode = (int) originalErrorAttributes.get("status");
        var status = getHttpStatusFromStatusCode(statusCode);

        var errorAttributes = new LinkedHashMap<String, Object>();
        errorAttributes.put("message", populateMapWithErrorMessage(error, originalErrorAttributes, status));
        errorAttributes.put("status", statusCode);
        errorAttributes.put("requestId", originalErrorAttributes.get("requestId"));
        errorAttributes.put("errors", List.of());

        return errorAttributes;
    }

    private HttpStatus getHttpStatusFromStatusCode(int statusCode) {
        return Arrays.stream(HttpStatus.values())
                .filter(v -> v.value() == statusCode)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(String.format(INVALID_STATUS_CODE_ERR, statusCode)));
    }

    private String populateMapWithErrorMessage(Throwable error, Map<String, Object> map, HttpStatus status) {
        return isErrorCausedByNoRouteDefinitionFound(error, map) ?
                String.format(ROUTE_DEFINITION_NOT_FOUND_ERR, map.get("path")) :
                trimStatusAndQuotesFromErrorMessage(error.getMessage(), status);
    }

    private boolean isErrorCausedByNoRouteDefinitionFound(Throwable error, Map<String, Object> map) {
        return map.containsKey("requestId") && error.getMessage().contains("404");
    }

    private String trimStatusAndQuotesFromErrorMessage(String errorMessage, HttpStatus status) {
        return errorMessage
                .replace(status.toString(), "")
                .replace("\"", "")
                .trim();
    }
}
