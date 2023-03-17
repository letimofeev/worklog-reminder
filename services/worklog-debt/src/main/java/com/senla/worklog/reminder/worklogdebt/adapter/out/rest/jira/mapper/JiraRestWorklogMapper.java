package com.senla.worklog.reminder.worklogdebt.adapter.out.rest.jira.mapper;

import com.senla.worklog.reminder.worklogdebt.adapter.out.rest.jira.dto.WorklogDto;
import com.senla.worklog.reminder.worklogdebt.domain.model.Worklog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JiraRestWorklogMapper {
    @Mapping(target = "id", source = "tempoWorklogId")
    @Mapping(target = "dateStarted", expression = "java(worklogDto.getStarted().toLocalDate())")
    @Mapping(target = "timeSpentSeconds", source = "timeSpentSeconds")
    @Mapping(target = "comment", source = "comment")
    @Mapping(target = "workerJiraKey", source = "worker")
    Worklog mapToDomain(WorklogDto worklogDto);
}
