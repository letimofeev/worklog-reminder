package com.senla.worklog.reminder.service;

import com.senla.worklog.reminder.model.v3.Author;

import java.util.List;

public interface AuthorsFetchStrategy {
    List<Author> getAuthors();
}
