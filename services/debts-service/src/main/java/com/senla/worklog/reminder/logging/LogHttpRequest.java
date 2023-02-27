package com.senla.worklog.reminder.logging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.Arrays;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LogHttpRequest {
    private String url;
    private HttpMethod method;
    private HttpHeaders headers;
    private String body;
    private Object[] parameters;

    @Override
    public String toString() {
        return "LogHttpRequest{" +
                "url='" + url + '\'' +
                ", method=" + method +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                ", parameters=" + Arrays.toString(parameters) +
                '}';
    }
}
