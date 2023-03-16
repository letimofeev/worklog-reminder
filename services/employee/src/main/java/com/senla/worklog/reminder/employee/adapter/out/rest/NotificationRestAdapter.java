package com.senla.worklog.reminder.employee.adapter.out.rest;

import com.senla.worklog.reminder.employee.adapter.out.rest.mapper.NotificationRestMapper;
import com.senla.worklog.reminder.employee.adapter.out.rest.model.NotificationUser;
import com.senla.worklog.reminder.employee.common.annotation.RestAdapter;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import com.senla.worklog.reminder.employee.domain.port.out.NotificationRestPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@RestAdapter
@RequiredArgsConstructor
public class NotificationRestAdapter implements NotificationRestPort {
    private final NotificationRestMapper restMapper;
    private final NotificationRestProperties restProperties;
    private final RestTemplate restTemplate;

    @Override
    public Employee getNotificationEmployee(Employee employee) {
        var login = employee.getSkypeLogin();
        var user = getUserByLogin(login)
                .orElse(new NotificationUser());

        return restMapper.mapToDomain(user);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        var login = employee.getSkypeLogin();
        var updatedEmployee = getUserByLogin(login)
                .map(user -> restMapper.mapToNotificationUser(employee, user.getId()))
                .map(this::updateNotificationUser)
                .map(restMapper::mapToDomain)
                .orElse(employee);

        employee.adjustNotificationStatus();
        return updatedEmployee;
    }

    @Override
    public void deleteEmployee(Employee employee) {
        var login = employee.getSkypeLogin();
        getUserByLogin(login)
                .ifPresent(user -> {
                    var uri = restProperties.getDeleteUserByLoginUri();
                    var userId = user.getId();
                    restTemplate.delete(uri, userId);
                });
    }

    private Optional<NotificationUser> getUserByLogin(String login) {
        var uri = fromUriString(restProperties.getGetUsersByLoginsUri())
                .buildAndExpand(login)
                .toUri();
        var users = restTemplate.getForEntity(uri, NotificationUser[].class).getBody();

        if (users != null && users.length > 0) {
            return Optional.of(users[0]);
        }
        return Optional.empty();
    }

    private NotificationUser updateNotificationUser(NotificationUser user) {
        var uri = restProperties.getUpdateUserUri();
        var headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        var requestEntity = new HttpEntity<>(user, headers);
        restTemplate.patchForObject(uri, requestEntity, Void.class);
        return user;
    }
}
