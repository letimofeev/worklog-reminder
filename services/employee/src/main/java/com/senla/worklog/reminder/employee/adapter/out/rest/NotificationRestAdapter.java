package com.senla.worklog.reminder.employee.adapter.out.rest;

import com.senla.worklog.reminder.annotation.DrivenAdapter;
import com.senla.worklog.reminder.employee.adapter.out.rest.dto.NotificationUserDto;
import com.senla.worklog.reminder.employee.adapter.out.rest.mapper.NotificationRestMapper;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import com.senla.worklog.reminder.employee.domain.port.out.NotificationRestPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

/**
 * This class represents a REST adapter for the notification service, responsible for sending HTTP requests and
 * invoking the corresponding API methods in the notification service.
 */
@Slf4j
@DrivenAdapter
@RequiredArgsConstructor
public class NotificationRestAdapter implements NotificationRestPort {
    private final NotificationRestMapper restMapper;
    private final NotificationRestProperties restProperties;
    private final RestTemplate restTemplate;

    @Override
    public Employee getNotificationEmployee(Employee employee) {
        var login = employee.getSkypeLogin();
        var user = getUserByLogin(login)
                .orElse(new NotificationUserDto());

        return restMapper.mapToDomain(user);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        var login = employee.getSkypeLogin();
        return getUserByLogin(login)
                .map(user -> restMapper.mapToNotificationUser(employee, user.getId()))
                .map(this::updateNotificationUser)
                .map(restMapper::mapToDomain)
                .orElse(employee);
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

    private Optional<NotificationUserDto> getUserByLogin(String login) {
        var uri = fromUriString(restProperties.getGetUsersByLoginsUri())
                .buildAndExpand(login)
                .toUri();
        var users = restTemplate.getForEntity(uri, NotificationUserDto[].class).getBody();

        if (users != null && users.length > 0) {
            return Optional.of(users[0]);
        }
        return Optional.empty();
    }

    private NotificationUserDto updateNotificationUser(NotificationUserDto user) {
        var uri = restProperties.getUpdateUserUri();
        var headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        var requestEntity = new HttpEntity<>(user, headers);
        restTemplate.patchForObject(uri, requestEntity, Void.class);
        return user;
    }
}
