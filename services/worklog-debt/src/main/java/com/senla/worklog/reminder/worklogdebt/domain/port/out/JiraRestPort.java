package com.senla.worklog.reminder.worklogdebt.domain.port.out;

import com.senla.worklog.reminder.worklogdebt.domain.model.Worklog;

import java.time.LocalDate;
import java.util.List;

public interface JiraRestPort {
    List<Worklog> getJiraWorklogs(LocalDate dateFrom, LocalDate dateTo);

    List<String> getJiraWorkersKeys(LocalDate dateFrom, LocalDate dateTo);
}
