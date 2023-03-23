package com.senla.worklog.reminder.worklogdebt.adapter.in.rest.dto;

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
public class ExcludedDayDto {
    private LocalDate date;
    private String reason;

    @Override
    public String toString() {
        return "ExcludedDayDto{" +
                "date=" + date +
                ", reason='" + reason + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExcludedDayDto that = (ExcludedDayDto) o;

        if (!Objects.equals(date, that.date)) return false;
        return Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (reason != null ? reason.hashCode() : 0);
        return result;
    }
}
