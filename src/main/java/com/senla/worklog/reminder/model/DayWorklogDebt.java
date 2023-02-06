package com.senla.worklog.reminder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DayWorklogDebt {
    private LocalDate date;
    private int timeDeptSeconds;

    @Override
    public String toString() {
        return "WorklogDebt{" +
                "date=" + date +
                ", timeDeptSeconds=" + timeDeptSeconds +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DayWorklogDebt that = (DayWorklogDebt) o;

        if (timeDeptSeconds != that.timeDeptSeconds) return false;
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + timeDeptSeconds;
        return result;
    }
}
