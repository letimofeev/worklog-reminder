package com.senla.worklog.reminder.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Worklog {
    private Long id;
    private LocalDate dateStarted;
    private Long timeSpentSeconds;
    private String comment;
    private Issue issue;
    private Worker worker;

    @Override
    public String toString() {
        return "Worklog{" +
                "id=" + id +
                ", dateStarted=" + dateStarted +
                ", timeSpentSeconds=" + timeSpentSeconds +
                ", comment='" + comment + '\'' +
                ", issue=" + issue +
                ", worker=" + worker +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Worklog worklog = (Worklog) o;

        if (!Objects.equals(id, worklog.id)) return false;
        if (!Objects.equals(dateStarted, worklog.dateStarted)) return false;
        if (!Objects.equals(timeSpentSeconds, worklog.timeSpentSeconds))
            return false;
        if (!Objects.equals(comment, worklog.comment)) return false;
        if (!Objects.equals(issue, worklog.issue)) return false;
        return Objects.equals(worker, worklog.worker);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dateStarted != null ? dateStarted.hashCode() : 0);
        result = 31 * result + (timeSpentSeconds != null ? timeSpentSeconds.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (issue != null ? issue.hashCode() : 0);
        result = 31 * result + (worker != null ? worker.hashCode() : 0);
        return result;
    }
}
