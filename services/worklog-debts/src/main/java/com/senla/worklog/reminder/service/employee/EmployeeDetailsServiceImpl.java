package com.senla.worklog.reminder.service.employee;

import com.senla.worklog.reminder.api.notification.client.NotificationClient;
import com.senla.worklog.reminder.api.notification.model.NotificationUser;
import com.senla.worklog.reminder.dto.EmployeeDetailsDto;
import com.senla.worklog.reminder.dto.EmployeeDto;
import com.senla.worklog.reminder.dto.mapper.EmployeeDetailsDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class EmployeeDetailsServiceImpl implements EmployeeDetailsService {
    private final EmployeeService employeeService;
    private final NotificationClient notificationClient;
    private final EmployeeDetailsDtoMapper detailsDtoMapper;

    @Override
    public List<EmployeeDetailsDto> getAllEmployeesDetails() {
        var employees = employeeService.getAllEmployees();
        var logins = getEmployeesLogins(employees);
        var usersByLogin = getUsersByLogin(logins);
        return employees.stream()
                .map(employee -> mapToEmployeeDetails(employee, usersByLogin))
                .collect(toList());
    }

    @Override
    public Optional<EmployeeDetailsDto> getEmployeeDetailsByJiraKey(String jiraKey) {
        return employeeService.getEmployeeByJiraKey(jiraKey)
                .map(this::getEmployeeDetailsDto);

    }

    @Override
    public EmployeeDetailsDto getEmployeeDetailsDto(EmployeeDto employee) {
        var login = employee.getSkypeLogin();
        var users = notificationClient.getAllUsersByLogins(List.of(login));
        if (users.isEmpty()) {
            return detailsDtoMapper.mapToDetails(employee);
        }
        var user = users.get(0);
        return detailsDtoMapper.mapToDetails(employee, user);
    }

    private List<String> getEmployeesLogins(List<EmployeeDto> employees) {
        return employees.stream()
                .map(EmployeeDto::getSkypeLogin)
                .collect(toList());
    }

    private Map<String, NotificationUser> getUsersByLogin(List<String> logins) {
        return notificationClient.getAllUsersByLogins(logins).stream()
                .collect(toMap(NotificationUser::getLogin, identity()));
    }

    private EmployeeDetailsDto mapToEmployeeDetails(EmployeeDto employee, Map<String, NotificationUser> usersByLogin) {
        var login = employee.getSkypeLogin();
        var user = usersByLogin.get(login);
        if (user == null) {
            return detailsDtoMapper.mapToDetails(employee);
        }
        return detailsDtoMapper.mapToDetails(employee, user);
    }
}
