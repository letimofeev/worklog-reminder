package com.senla.worklog.reminder.logging;

import com.senla.worklog.reminder.config.JiraProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.lang.String.join;
import static java.util.stream.Collectors.joining;

@Component
@RequiredArgsConstructor
public class LogMessageBuilderImpl implements LogMessageBuilder {
    private final JiraProperties jiraProperties;

    private static final String DEFAULT_DESCRIPTION = "Making an http request:";
    private static final String PASSWORD_HIDER = "[hidden]";

    @Override
    public String buildRequestLogMessage(LogHttpRequest request) {
        return buildRequestLogMessage(DEFAULT_DESCRIPTION, request);
    }

    @Override
    public String buildRequestLogMessage(String header, LogHttpRequest request) {
        String headers = buildHeaders(request.getHeaders());
        String logMessage = header + "\n\t" +
                "Request URL                     " + request.getUrl() + "\n\t" +
                "Request Method                  " + request.getMethod() + "\n\t" +
                "Request Headers                 " + headers + "\n\t";
        if (request.getParameters() != null) {
            String parameters = buildRequestParameters(request.getParameters());
            logMessage += "Request Parameters              " + parameters + "\n\t";
        }
        if (request.getBody() != null) {
            String body = hidePasswordIfRequired(request.getBody());
            logMessage += "Request Body                    " + body + "\n";
        }
        return logMessage;
    }

    private String hidePasswordIfRequired(String body) {
        if (!jiraProperties.getDebug().isPasswordEnabled()) {
            String basicPassword = jiraProperties.getBasicAuth().getPassword();
            String formPassword = jiraProperties.getFormAuth().getPassword();
            return body.replace(basicPassword, PASSWORD_HIDER)
                    .replace(formPassword, PASSWORD_HIDER);
        }
        return body;
    }

    private String buildHeaders(HttpHeaders headers) {
        return headers.entrySet().stream()
                .map(this::hideAuthorizationHeaderIfRequired)
                .collect(joining(", "));
    }

    private String hideAuthorizationHeaderIfRequired(Map.Entry<String, List<String>> entry) {
        String key = entry.getKey();
        boolean debugPasswordEnabled = jiraProperties.getDebug().isPasswordEnabled();
        String value;
        if (key.equalsIgnoreCase("authorization") && !debugPasswordEnabled) {
            value = PASSWORD_HIDER;
        } else {
            value = join(", ", entry.getValue());
        }
        return key + ": " + value;
    }

    private String buildRequestParameters(Object[] parameters) {
        return Arrays.stream(parameters)
                .map(Object::toString)
                .collect(joining(", "));
    }
}