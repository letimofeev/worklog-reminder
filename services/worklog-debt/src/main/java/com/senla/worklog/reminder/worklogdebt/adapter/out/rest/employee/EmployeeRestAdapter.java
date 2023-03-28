package com.senla.worklog.reminder.worklogdebt.adapter.out.rest.employee;

import com.senla.worklog.reminder.annotation.DrivenAdapter;
import com.senla.worklog.reminder.worklogdebt.adapter.out.rest.employee.dto.EmployeeDto;
import com.senla.worklog.reminder.worklogdebt.adapter.out.rest.employee.mapper.EmployeeRestMapper;
import com.senla.worklog.reminder.worklogdebt.domain.model.EmployeeWorklogDebts;
import com.senla.worklog.reminder.worklogdebt.domain.port.out.EmployeeRestPort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@DrivenAdapter
@RequiredArgsConstructor
public class EmployeeRestAdapter implements EmployeeRestPort {
    private final EmployeeRestMapper restMapper;
    private final EmployeeRestProperties restProperties;
    private final RestTemplate restTemplate;

    @Override
    public EmployeeWorklogDebts getEmployee(EmployeeWorklogDebts worklogDebts) {
        var jiraKey = worklogDebts.getJiraKey();
        var employee = getEmployeeByJiraKey(jiraKey).orElse(new EmployeeDto());
        return restMapper.mapToDomain(employee);
    }

    private Optional<EmployeeDto> getEmployeeByJiraKey(String jiraKey) {
        var uri = fromUriString(restProperties.getGetEmployeeByJiraKeyUri())
                .buildAndExpand(jiraKey)
                .toUri();
        var employees = restTemplate.getForEntity(uri, EmployeeDto[].class).getBody();
        if (employees != null && employees.length > 0) {
            return Optional.of(employees[0]);
        }
        return Optional.empty();
    }
}
