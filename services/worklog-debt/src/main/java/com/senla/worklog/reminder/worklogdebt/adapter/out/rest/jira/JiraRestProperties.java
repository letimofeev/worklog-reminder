package com.senla.worklog.reminder.worklogdebt.adapter.out.rest.jira;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "jira")
public class JiraRestProperties {
    private String loginUrl;
    private String baseUrl;
    private DebugOptions debug;
    private AuthProperties basicAuth;
    private AuthProperties formAuth;
    private Rest rest;

    public String getV4GetWorklogDebtsUri() {
        return rest.api.v4.getWorklogsUri;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthProperties {
        private String username;
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DebugOptions {
        private boolean passwordEnabled;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Rest {
        private Api api;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Api {
        private V4 v4;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class V4 {
        private String getWorklogsUri;
    }
}
