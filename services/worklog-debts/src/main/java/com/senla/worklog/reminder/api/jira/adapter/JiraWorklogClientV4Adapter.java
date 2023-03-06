package com.senla.worklog.reminder.api.jira.adapter;

import com.senla.worklog.reminder.api.jira.v4.client.JiraWorklogClientV4;
import com.senla.worklog.reminder.model.Worklog;
import com.senla.worklog.reminder.api.jira.adapter.mapper.WorklogV4Mapper;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class JiraWorklogClientV4Adapter implements JiraWorklogClientAdapter {
    private final JiraWorklogClientV4 worklogClient;
    private final WorklogV4Mapper mapper;

    @Override
    public List<Worklog> getAllForPeriod(LocalDate dateFrom, LocalDate dateTo) {
        return worklogClient.getAllForPeriod(dateFrom, dateTo).stream()
                .map(mapper::mapToModel)
                .collect(toList());
    }
}
