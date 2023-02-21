package com.senla.worklog.reminder.service;

import com.senla.worklog.reminder.api.v3.model.AuthorV3;

import java.util.List;

public interface AuthorsFetchStrategy {
    List<AuthorV3> getAuthors();
}
