package com.senla.worklog.reminder.employee.adapter.out.jpa.repository;

import com.senla.worklog.reminder.employee.adapter.out.jpa.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The {@code EmployeeJpaRepository} interface defines a set of methods for working
 * with {@link EmployeeEntity} entities using Spring Data JPA.
 *
 * @since 0.0.1
 */
public interface EmployeeJpaRepository extends JpaRepository<EmployeeEntity, Long> {

    /**
     * Finds an employee entity by its Jira key.
     *
     * @param jiraKey the Jira key to search for
     * @return an optional containing the matching employee entity, or an empty optional if not found
     */
    Optional<EmployeeEntity> findByJiraKey(String jiraKey);

    /**
     * Finds an employee entity by its Skype login.
     *
     * @param skypeLogin the Skype login to search for
     * @return an optional containing the matching employee entity, or an empty optional if not found
     */
    Optional<EmployeeEntity> findBySkypeLogin(String skypeLogin);
}
