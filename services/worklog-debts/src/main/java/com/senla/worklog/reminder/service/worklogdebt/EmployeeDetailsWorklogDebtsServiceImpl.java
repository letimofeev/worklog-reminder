package com.senla.worklog.reminder.service.worklogdebt;

import com.senla.worklog.reminder.dto.*;
import com.senla.worklog.reminder.model.Worker;
import com.senla.worklog.reminder.service.employee.EmployeeDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeDetailsWorklogDebtsServiceImpl implements EmployeeDetailsWorklogDebtsService {
    private final EmployeeDetailsService employeeDetailsService;
    private final WorkerWorklogDebtsService workerDebtsService;

    @Override
    public List<EmployeeDetailsWorklogDebtsDto> getEmployeesDetailsDebts(LocalDate dateFrom, LocalDate dateTo) {
        var workersDebts = workerDebtsService.getWorkersDebts(dateFrom, dateTo);
        return mapToEmployeeDetailsDebts(workersDebts);
    }

    private List<EmployeeDetailsWorklogDebtsDto> mapToEmployeeDetailsDebts(List<WorkerWorklogDebtsDto> workersDebts) {
        return workersDebts.stream()
                .map(workerDebts -> {
                    var worker = workerDebts.getWorker();
                    var worklogDebts = workerDebts.getWorklogDebts();
                    var employeeDetails = employeeDetailsService.getEmployeeDetailsByJiraKey(worker.getKey())
                            .orElseGet(() -> handleEmployeeNotFound(worker));
                    return new EmployeeDetailsWorklogDebtsDto(employeeDetails, worklogDebts);
                })
                .collect(toList());
    }

    // TODO: remove copy paste
    private EmployeeDetailsDto handleEmployeeNotFound(Worker worker) {
        var employee = new EmployeeDetailsDto()
                .setFirstName(worker.getDisplayName())
                .setJiraKey(worker.getKey());
        log.warn("Employee with jiraKey = '" + worker.getKey() + "' not found");
        return employee;
    }
}
