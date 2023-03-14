package com.senla.worklog.reminder.repository;

import com.senla.worklog.reminder.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByJiraKey(String jiraKey);

    List<Employee> findAllByJiraKeyIn(Collection<String> jiraKey);
}
