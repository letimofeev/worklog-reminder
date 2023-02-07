package com.senla.worklog.reminder.model;

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
public class DayWorklogDebt {
    private LocalDate date;
    private Long timeDeptSeconds;

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
