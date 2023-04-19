package com.senla.worklog.reminder.employee.adapter.out.jpa;

import com.senla.worklog.reminder.employee.adapter.out.jpa.entity.EmployeeEntity;
import com.senla.worklog.reminder.employee.adapter.out.jpa.entity.RegionEntity;
import com.senla.worklog.reminder.employee.adapter.out.jpa.mapper.EmployeeEntityMapper;
import com.senla.worklog.reminder.employee.adapter.out.jpa.repository.EmployeeJpaRepository;
import com.senla.worklog.reminder.employee.adapter.out.jpa.repository.RegionJpaRepository;
import com.senla.worklog.reminder.employee.domain.exception.EmployeeNotFoundException;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeJpaAdapterTest {
    @Mock
    private EmployeeJpaRepository employeeRepository;

    @Mock
    private RegionJpaRepository regionRepository;

    @Mock
    private EmployeeEntityMapper entityMapper;

    @InjectMocks
    private EmployeeJpaAdapter employeeJpaAdapter;

    @Test
    void addEmployee_shouldSaveEmployeeAndReturnMappedDomainObject() {
        var employee = new Employee().setId(11L).setFirstName("Name");
        var employeeEntity = new EmployeeEntity();
        employeeEntity.setFirstName("Name");
        var regionId = randomUUID();
        var region = new RegionEntity(regionId, "Name");
        employeeEntity.setRegion(region);

        when(entityMapper.mapToJpaEntity(employee)).thenReturn(employeeEntity);
        when(employeeRepository.save(employeeEntity)).thenReturn(employeeEntity);
        when(regionRepository.findById(regionId)).thenReturn(Optional.of(region));
        when(entityMapper.mapToDomain(employeeEntity)).thenReturn(employee);

        var result = employeeJpaAdapter.addEmployee(employee);

        assertEquals(employee, result);

        verify(entityMapper).mapToJpaEntity(employee);
        verify(employeeRepository).save(employeeEntity);
        verify(entityMapper).mapToDomain(employeeEntity);
    }

    @Test
    void getEmployeeById_shouldReturnMappedDomainObject_whenEmployeeExists() {
        var id = 1L;
        var employeeEntity = mock(EmployeeEntity.class);
        var expectedEmployee = mock(Employee.class);
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employeeEntity));
        when(entityMapper.mapToDomain(employeeEntity)).thenReturn(expectedEmployee);

        var result = employeeJpaAdapter.getEmployeeById(id);

        assertEquals(expectedEmployee, result);

        verify(employeeRepository).findById(id);
        verify(entityMapper).mapToDomain(employeeEntity);
    }

    @Test
    void getEmployeeById_shouldThrowException_whenEmployeeDoesNotExist() {
        var id = 1L;
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeJpaAdapter.getEmployeeById(id));
        verify(employeeRepository).findById(id);
    }

    @Test
    public void getEmployeesByJiraKey_shouldReturnListOfEmployees_whenEmployeeReturnsEntities() {
        var jiraKey = "TEST-123";
        var employeeEntity = new EmployeeEntity();
        employeeEntity.setId(100L);
        employeeEntity.setJiraKey(jiraKey);

        var employeeEntities = List.of(employeeEntity);

        when(employeeRepository.findByJiraKey(jiraKey)).thenReturn(Optional.of(employeeEntity));
        when(entityMapper.mapToDomain(any(EmployeeEntity.class))).thenReturn(new Employee()
                .setId(100L)
                .setJiraKey(jiraKey));

        var employees = employeeJpaAdapter.getEmployeesByJiraKey(jiraKey);

        assertEquals(employeeEntities.size(), employees.size());
        assertEquals(jiraKey, employees.get(0).getJiraKey());
    }

    @Test
    public void getAllEmployees_shouldReturnListOfEmployees_when_EmployeeReturnsEntities() {
        var employeeEntities = List.of(new EmployeeEntity());

        when(employeeRepository.findAll()).thenReturn(employeeEntities);
        when(entityMapper.mapToDomain(any(EmployeeEntity.class))).thenReturn(new Employee());

        var employees = employeeJpaAdapter.getAllEmployees();

        assertEquals(employeeEntities.size(), employees.size());
    }

    @Test
    public void updateEmployee_shouldReturnUpdatedEmployee_whenEmployeeExistsById() {
        var id = 1L;
        var employee = new Employee();
        employee.setId(id);
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(id);
        var regionId = randomUUID();
        var region = new RegionEntity(regionId, "Name");
        employeeEntity.setRegion(region);

        when(employeeRepository.existsById(id)).thenReturn(true);
        when(entityMapper.mapToJpaEntity(employee)).thenReturn(employeeEntity);
        when(employeeRepository.save(employeeEntity)).thenReturn(employeeEntity);
        when(regionRepository.findById(regionId)).thenReturn(Optional.of(region));
        when(entityMapper.mapToDomain(employeeEntity)).thenReturn(employee);

        var updatedEmployee = employeeJpaAdapter.updateEmployee(employee);

        assertNotNull(updatedEmployee);
        assertEquals(employee.getId(), updatedEmployee.getId());
    }

    @Test
    public void updateEmployee_shouldThrowEmployeeNotFoundException_whenEmployeeDoesNotExistById() {
        var id = 1L;
        var employee = new Employee();
        employee.setId(id);

        when(employeeRepository.existsById(id)).thenReturn(false);

        assertThrows(EmployeeNotFoundException.class, () -> employeeJpaAdapter.updateEmployee(employee));
    }

    @Test
    void deleteEmployeeById_shouldDeleteEmployeeById() {
        var id = 1L;

        employeeJpaAdapter.deleteEmployeeById(id);

        verify(employeeRepository).deleteById(id);
    }
}
