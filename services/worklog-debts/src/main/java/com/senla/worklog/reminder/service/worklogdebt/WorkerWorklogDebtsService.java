package com.senla.worklog.reminder.service.worklogdebt;

import com.senla.worklog.reminder.dto.WorkerWorklogDebtsDto;

import java.time.LocalDate;
import java.util.List;

public interface WorkerWorklogDebtsService {
    List<WorkerWorklogDebtsDto> getWorkersDebts(LocalDate dateFrom, LocalDate dateTo);
}
