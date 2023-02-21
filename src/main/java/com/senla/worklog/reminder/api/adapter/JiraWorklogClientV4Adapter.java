package com.senla.worklog.reminder.api.adapter;

import com.senla.worklog.reminder.api.v4.client.JiraWorklogClientV4;
import com.senla.worklog.reminder.model.Worklog;
import com.senla.worklog.reminder.model.mapper.WorklogV4Mapper;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JiraWorklogClientV4Adapter implements JiraWorklogClientAdapter {
    private final JiraWorklogClientV4 worklogClient;
    private final WorklogV4Mapper mapper;

    @Override
    public List<Worklog> getAllForPeriod(LocalDate dateFrom, LocalDate dateTo) {
        return worklogClient.getAllForPeriod(dateFrom, dateTo).stream()
                .map(mapper::mapToModel)
                .collect(Collectors.toList());
    }
}
