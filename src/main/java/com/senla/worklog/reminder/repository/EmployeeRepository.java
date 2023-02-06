package com.senla.worklog.reminder.repository;

import com.senla.worklog.reminder.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
