package com.senla.worklog.reminder.worklogdebt.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DayWorklogDebtDto {
    @Schema(description = "Date in ISO format", example = "2022-02-22")
    private LocalDate date;

    @Schema(description = "Employee's worklog dept for a given day in seconds", example = "28800")
    private Long timeDeptSeconds;

    @Override
    public String toString() {
        return "DayWorklogDebtDto{" +
                "date=" + date +
                ", timeDeptSeconds=" + timeDeptSeconds +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DayWorklogDebtDto that = (DayWorklogDebtDto) o;

        if (!Objects.equals(date, that.date)) return false;
        return Objects.equals(timeDeptSeconds, that.timeDeptSeconds);
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (timeDeptSeconds != null ? timeDeptSeconds.hashCode() : 0);
        return result;
    }
}
