package com.senla.worklog.reminder.employee.adapter.out.rest;

import com.senla.worklog.reminder.employee.adapter.out.rest.dto.NotificationUserDto;
import com.senla.worklog.reminder.employee.adapter.out.rest.mapper.NotificationRestMapper;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import com.senla.worklog.reminder.employee.domain.model.NotificationStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@ExtendWith(MockitoExtension.class)
class NotificationRestAdapterTest {

    @Mock
    private NotificationRestMapper mapper;

    @Mock
    private NotificationRestProperties properties;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private NotificationRestAdapter adapter;

    @Test
    public void getNotificationEmployee_shouldReturnMapped_employeeWhenUserExists() {
        var employee = new Employee().setSkypeLogin("test_login");

        NotificationUserDto userDto = new NotificationUserDto();
        userDto.setId(99L);

        when(properties.getGetUsersByLoginsUri()).thenReturn("test_uri");
        when(restTemplate.getForEntity(fromUriString("test_uri").build().toUri(), NotificationUserDto[].class))
                .thenReturn(ok(new NotificationUserDto[]{userDto}));
        when(mapper.mapToDomain(userDto)).thenReturn(employee);

        var actual = adapter.getNotificationEmployee(employee);

        assertEquals(employee, actual);

        verify(restTemplate).getForEntity(fromUriString("test_uri").build().toUri(), NotificationUserDto[].class);
        verify(mapper).mapToDomain(userDto);
    }

    @Test
    public void getNotificationEmployee_shouldReturnOriginalEmployee_whenUserDoesNotExist() {
        var employee = new Employee().setSkypeLogin("test_login");

        when(properties.getGetUsersByLoginsUri()).thenReturn("test_uri");
        when(restTemplate.getForEntity(fromUriString("test_uri").build().toUri(), NotificationUserDto[].class))
                .thenReturn(ok(new NotificationUserDto[]{}));
        when(mapper.mapToDomain(new NotificationUserDto())).thenReturn(employee);

        var actual = adapter.getNotificationEmployee(employee);

        assertEquals(employee, actual);

        verify(restTemplate).getForEntity(fromUriString("test_uri").build().toUri(), NotificationUserDto[].class);
        verify(mapper).mapToDomain(new NotificationUserDto());
    }

    @Test
    public void getNotificationEmployee_shouldReturnOriginalEmployee_whenResponseBodyIsNull() {
        var employee = new Employee().setSkypeLogin("test_login");

        when(properties.getGetUsersByLoginsUri()).thenReturn("test_uri");
        when(restTemplate.getForEntity(fromUriString("test_uri").build().toUri(), NotificationUserDto[].class))
                .thenReturn(ok(null));
        when(mapper.mapToDomain(new NotificationUserDto())).thenReturn(employee);

        var actual = adapter.getNotificationEmployee(employee);

        assertEquals(employee, actual);

        verify(restTemplate).getForEntity(fromUriString("test_uri").build().toUri(), NotificationUserDto[].class);
        verify(mapper).mapToDomain(new NotificationUserDto());
    }

    @Test
    public void updateEmployee_shouldReturnUpdatedEmployee_whenUserExists() {
        var employee = new Employee()
                .setSkypeLogin("test_login")
                .setNotificationStatus(new NotificationStatus().setNotificationEnabled(true));
        var userDto = new NotificationUserDto().setId(98L);

        when(properties.getGetUsersByLoginsUri()).thenReturn("test_uri");
        when(properties.getUpdateUserUri()).thenReturn("test_uri");
        when(restTemplate.getForEntity(fromUriString("test_uri").build().toUri(), NotificationUserDto[].class))
                .thenReturn(ok(new NotificationUserDto[]{userDto}));

        var updatedUserDto = new NotificationUserDto().setId(98L).setEnabled(true);

        when(mapper.mapToNotificationUser(employee, 98L)).thenReturn(updatedUserDto);
        when(mapper.mapToDomain(updatedUserDto)).thenReturn(employee.setNotificationStatus(new NotificationStatus(true, true)));

        var actual = adapter.updateEmployee(employee);

        assertEquals(employee, actual);
        assertTrue(employee.getNotificationStatus().isNotificationEnabled());

        verify(restTemplate).getForEntity(fromUriString("test_uri").build().toUri(), NotificationUserDto[].class);
        verify(mapper).mapToNotificationUser(employee, 98L);
        verify(restTemplate).patchForObject(eq("test_uri"), any(HttpEntity.class), eq(Void.class));
        verify(mapper).mapToDomain(updatedUserDto);
    }

    @Test
    public void deleteEmployee_shouldDeleteUser_whenUserExists() {
        var employee = new Employee().setSkypeLogin("test_login");

        var userDto = new NotificationUserDto();
        userDto.setId(99L);

        when(properties.getGetUsersByLoginsUri()).thenReturn("test_uri");
        when(properties.getDeleteUserByLoginUri()).thenReturn("delete_test_uri");
        when(restTemplate.getForEntity(fromUriString("test_uri").build().toUri(), NotificationUserDto[].class))
                .thenReturn(ok(new NotificationUserDto[]{userDto}));

        adapter.deleteEmployee(employee);

        verify(restTemplate).delete("delete_test_uri", 99L);
    }

    @Test
    public void deleteEmployee_shouldDoNothing_whenUserDoesNotExists() {
        var employee = new Employee().setSkypeLogin("test_login");

        var userDto = new NotificationUserDto();
        userDto.setId(99L);

        when(properties.getGetUsersByLoginsUri()).thenReturn("test_uri");
        when(restTemplate.getForEntity(fromUriString("test_uri").build().toUri(), NotificationUserDto[].class))
                .thenReturn(ok(new NotificationUserDto[]{}));

        adapter.deleteEmployee(employee);

        verify(restTemplate, never()).delete(anyString(), anyLong());
    }
}
