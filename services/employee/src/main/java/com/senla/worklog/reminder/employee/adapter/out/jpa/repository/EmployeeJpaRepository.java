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
     * Finds an employee entity by its JIRA key.
     *
     * @param jiraKey the JIRA key to search for
     * @return an optional containing the matching employee entity, or an empty optional if not found
     */
    Optional<EmployeeEntity> findByJiraKey(String jiraKey);

    /**
     * Checks whether an employee entity exists with the given Skype login.
     *
     * @param skypeLogin the Skype login to search for
     * @return true if an employee entity with the given Skype login exists, false otherwise
     */
    boolean existsBySkypeLogin(String skypeLogin);

    /**
     * Checks whether an employee entity exists with the given JIRA key.
     *
     * @param jiraKey the JIRA key to search for
     * @return true if an employee entity with the given JIRA key exists, false otherwise
     */
    boolean existsByJiraKey(String jiraKey);
}
