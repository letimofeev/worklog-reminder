package com.senla.worklog.reminder.service;

import com.senla.worklog.reminder.model.Author;

import java.util.List;

public interface AuthorsFetchStrategy {
    List<Author> getAuthors();
}
