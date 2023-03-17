package com.senla.worklog.reminder.dto;

import com.senla.worklog.reminder.model.Worker;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WorkerWorklogDebtsDto {
    private Worker worker;
    private List<DayWorklogDebtDto> worklogDebts;

    @Override
    public String toString() {
        return "WorkerWorklogDebtsDto{" +
                "worker=" + worker +
                ", worklogDebts=" + worklogDebts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkerWorklogDebtsDto that = (WorkerWorklogDebtsDto) o;

        if (!Objects.equals(worker, that.worker)) return false;
        return Objects.equals(worklogDebts, that.worklogDebts);
    }

    @Override
    public int hashCode() {
        int result = worker != null ? worker.hashCode() : 0;
        result = 31 * result + (worklogDebts != null ? worklogDebts.hashCode() : 0);
        return result;
    }
}
