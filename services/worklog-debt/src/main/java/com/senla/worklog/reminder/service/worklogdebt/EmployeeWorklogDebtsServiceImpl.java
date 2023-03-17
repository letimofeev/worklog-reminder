package com.senla.worklog.reminder.service.worklogdebt;

import com.senla.worklog.reminder.dto.EmployeeDto;
import com.senla.worklog.reminder.dto.EmployeeWorklogDebtsDto;
import com.senla.worklog.reminder.dto.WorkerWorklogDebtsDto;
import com.senla.worklog.reminder.model.Worker;
import com.senla.worklog.reminder.service.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeWorklogDebtsServiceImpl implements EmployeeWorklogDebtsService {
    private final EmployeeService employeeService;
    private final WorkerWorklogDebtsService workerDebtsService;

    @Override
    public List<EmployeeWorklogDebtsDto> getEmployeesDebts(LocalDate dateFrom, LocalDate dateTo) {
        var workersDebts = workerDebtsService.getWorkersDebts(dateFrom, dateTo);
        return mapToEmployeeDebts(workersDebts);
    }

    private List<EmployeeWorklogDebtsDto> mapToEmployeeDebts(List<WorkerWorklogDebtsDto> workersDebts) {
        return workersDebts.stream()
                .map(workerDebts -> {
                    var worker = workerDebts.getWorker();
                    var worklogDebts = workerDebts.getWorklogDebts();
                    var employee = employeeService.getEmployeeByJiraKey(worker.getKey())
                            .orElseGet(() -> handleEmployeeNotFound(worker));
                    return new EmployeeWorklogDebtsDto(employee, worklogDebts);
                })
                .collect(toList());
    }

    private EmployeeDto handleEmployeeNotFound(Worker worker) {
        var employee = new EmployeeDto()
                .setFirstName(worker.getDisplayName())
                .setJiraKey(worker.getKey());
        log.warn("Employee with jiraKey = '" + worker.getKey() + "' not found");
        return employee;
    }
}
