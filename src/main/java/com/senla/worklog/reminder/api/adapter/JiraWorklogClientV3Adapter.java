package com.senla.worklog.reminder.api.adapter;

import com.senla.worklog.reminder.api.v3.client.JiraWorklogClientV3;
import com.senla.worklog.reminder.model.Worklog;
import com.senla.worklog.reminder.model.mapper.WorklogV3Mapper;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JiraWorklogClientV3Adapter implements JiraWorklogClientAdapter {
    private final JiraWorklogClientV3 worklogClient;
    private final WorklogV3Mapper mapper;

    @Override
    public List<Worklog> getAllForPeriod(LocalDate dateFrom, LocalDate dateTo) {
        return worklogClient.getAllForPeriod(dateFrom, dateTo).stream()
                .map(mapper::mapToModel)
                .collect(Collectors.toList());
    }
}
