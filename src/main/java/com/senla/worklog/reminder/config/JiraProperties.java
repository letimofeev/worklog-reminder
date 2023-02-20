package com.senla.worklog.reminder.config;

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
public class JiraProperties {
    private String worklogsUrlTemplate;
    private String loginUrl;
    private DebugOptions debug;
    private AuthProperties basicAuth;
    private AuthProperties formAuth;

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
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DebugOptions {
        private boolean passwordEnabled;
    }
}
