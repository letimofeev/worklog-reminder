package com.senla.worklog.reminder.worklogdebt.application.service.mapper;

import com.senla.worklog.reminder.worklogdebt.domain.model.EmployeeWorklogDebts;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorklogDebtsServiceMapper {
    @Mapping(target = "id", source = "employeeRestDebts.id")
    @Mapping(target = "firstName", source = "employeeRestDebts.firstName")
    @Mapping(target = "lastName", source = "employeeRestDebts.lastName")
    @Mapping(target = "jiraKey", source = "employeeRestDebts.jiraKey")
    @Mapping(target = "skypeLogin", source = "employeeRestDebts.skypeLogin")
    @Mapping(target = "notificationStatus", source = "employeeRestDebts.notificationStatus")
    @Mapping(target = "region", source = "employeeRestDebts.region")
    @Mapping(target = "dateFrom", source = "vacationRestDebts.dateFrom")
    @Mapping(target = "dateTo", source = "vacationRestDebts.dateTo")
    @Mapping(target = "worklogDebtsCount", expression = "java(jiraDebts.getWorklogDebts().size())")
    @Mapping(target = "worklogDebts", source = "jiraDebts.worklogDebts")
    @Mapping(target = "excludedDays", source = "vacationRestDebts.excludedDays")
    EmployeeWorklogDebts mergeDomains(EmployeeWorklogDebts jiraDebts,
                                      EmployeeWorklogDebts employeeRestDebts,
                                      EmployeeWorklogDebts vacationRestDebts);
}
