package com.senla.worklog.reminder.service.notification;

import com.senla.worklog.reminder.config.JiraProperties;
import com.senla.worklog.reminder.dto.EmployeeWorklogDebtsDto;
import com.senla.worklog.reminder.model.DayWorklogDebt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationMessageBuilderImpl implements NotificationMessageBuilder {
    private final JiraProperties jiraProperties;

    @Override
    public String buildMessage(EmployeeWorklogDebtsDto employeeDebts) {
        var name = employeeDebts.getEmployee().getFirstName();
        var worklogDebts = employeeDebts.getWorklogDebts();
        var host = jiraProperties.getHost();
        return String.format("NOTIFICATION\n" +
                "\n" +
                "Hello, %s!\n" +
                "You need to log the time in Jira. Missing work logs for the following dates:\n" +
                "\n" +
                formatMissingWorklogs(worklogDebts) +
                "\n" +
                "You can visit %s to log time", name, host);
    }

    private String formatMissingWorklogs(List<DayWorklogDebt> worklogDebts) {
        return worklogDebts.stream()
                .map(this::formatMissingDayWorklogs)
                .collect(Collectors.joining("\n"));
    }

    private String formatMissingDayWorklogs(DayWorklogDebt worklogDebt) {
        return String.format("Date: %s\n" +
                "Required log time: %s\n",
                worklogDebt.getDate(),
                formatRequiredTime(worklogDebt.getTimeDeptSeconds()));
    }

    private String formatRequiredTime(long seconds) {
        var hours = seconds / 3600;
        var minutes = (seconds % 3600) / 60;
        var remainingSeconds = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }
}
