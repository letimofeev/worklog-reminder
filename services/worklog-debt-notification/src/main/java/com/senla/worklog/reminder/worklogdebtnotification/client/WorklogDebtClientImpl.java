package com.senla.worklog.reminder.worklogdebtnotification.client;

import com.senla.worklog.reminder.worklogdebtnotification.config.WorklogDebtsProperties;
import com.senla.worklog.reminder.worklogdebtnotification.model.EmployeeWorklogDebts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@Component
@RequiredArgsConstructor
public class WorklogDebtClientImpl implements WorklogDebtClient {
    private final RestTemplate restTemplate;
    private final WorklogDebtsProperties worklogDebtsProperties;

    @Override
    public List<EmployeeWorklogDebts> getEmployeesDebts(LocalDate dateFrom, LocalDate dateTo) {
        var uri = fromUriString(worklogDebtsProperties.getGetWorklogDebtsUri())
                .buildAndExpand(dateFrom, dateTo)
                .toUri();
        var worklogDebts = restTemplate.getForEntity(uri, EmployeeWorklogDebts[].class).getBody();
        Objects.requireNonNull(worklogDebts, "Response body from worklog-debt service must not be null");
        return Arrays.stream(worklogDebts).collect(toList());
    }
}
