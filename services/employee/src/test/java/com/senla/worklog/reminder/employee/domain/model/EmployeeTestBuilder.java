package com.senla.worklog.reminder.employee.domain.model;

import java.util.UUID;

public class EmployeeTestBuilder {
    public static final Long TEST_ID = 1001L;
    public static final String TEST_FIRST_NAME = "John";
    public static final String TEST_LAST_NAME = "Doe";
    public static final String TEST_JIRA_KEY = "jira-key-123";
    public static final String TEST_SKYPE_LOGIN = "skype-login-321";

    public static final Region TEST_REGION = new Region(UUID.fromString("ac7bc9b7-1515-4d57-a825-5001a83f8089"), "Georgia");
    public static final NotificationStatus TEST_NOTIFICATION_STATUS = new NotificationStatus(false, true);

    private final Employee employee = new Employee();

    public EmployeeTestBuilder defaultEmployee() {
        jpaEmployee();
        restEmployee();

        return this;
    }

    public EmployeeTestBuilder jpaEmployee() {
        employee.setId(TEST_ID)
                .setFirstName(TEST_FIRST_NAME)
                .setLastName(TEST_LAST_NAME)
                .setRegion(TEST_REGION)
                .setJiraKey(TEST_JIRA_KEY)
                .setSkypeLogin(TEST_SKYPE_LOGIN);

        return this;
    }

    public EmployeeTestBuilder restEmployee() {
        employee.setNotificationStatus(TEST_NOTIFICATION_STATUS);

        return this;
    }

    public Employee build() {
        return employee;
    }
}
