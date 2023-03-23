
package com.senla.worklog.reminder.worklogdebt.adapter.out.rest.jira.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WorklogDto {
    private Object attributes;
    private Long billableSeconds;
    private String comment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime dateCreated;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime dateUpdated;
    private IssueDto issue;
    private Long originId;
    private Long originTaskId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime started;

    private Long tempoWorklogId;
    private String timeSpent;
    private Long timeSpentSeconds;
    private String updater;
    private String worker;

    @Override
    public String toString() {
        return "Worklog{" +
                "attributes=" + attributes +
                ", billableSeconds=" + billableSeconds +
                ", comment='" + comment + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateUpdated=" + dateUpdated +
                ", issue=" + issue +
                ", originId=" + originId +
                ", originTaskId=" + originTaskId +
                ", started=" + started +
                ", tempoWorklogId=" + tempoWorklogId +
                ", timeSpent='" + timeSpent + '\'' +
                ", timeSpentSeconds=" + timeSpentSeconds +
                ", updater='" + updater + '\'' +
                ", worker='" + worker + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorklogDto that = (WorklogDto) o;

        if (!Objects.equals(attributes, that.attributes)) return false;
        if (!Objects.equals(billableSeconds, that.billableSeconds))
            return false;
        if (!Objects.equals(comment, that.comment)) return false;
        if (!Objects.equals(dateCreated, that.dateCreated)) return false;
        if (!Objects.equals(dateUpdated, that.dateUpdated)) return false;
        if (!Objects.equals(issue, that.issue)) return false;
        if (!Objects.equals(originId, that.originId)) return false;
        if (!Objects.equals(originTaskId, that.originTaskId)) return false;
        if (!Objects.equals(started, that.started)) return false;
        if (!Objects.equals(tempoWorklogId, that.tempoWorklogId))
            return false;
        if (!Objects.equals(timeSpent, that.timeSpent)) return false;
        if (!Objects.equals(timeSpentSeconds, that.timeSpentSeconds))
            return false;
        if (!Objects.equals(updater, that.updater)) return false;
        return Objects.equals(worker, that.worker);
    }

    @Override
    public int hashCode() {
        int result = attributes != null ? attributes.hashCode() : 0;
        result = 31 * result + (billableSeconds != null ? billableSeconds.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        result = 31 * result + (dateUpdated != null ? dateUpdated.hashCode() : 0);
        result = 31 * result + (issue != null ? issue.hashCode() : 0);
        result = 31 * result + (originId != null ? originId.hashCode() : 0);
        result = 31 * result + (originTaskId != null ? originTaskId.hashCode() : 0);
        result = 31 * result + (started != null ? started.hashCode() : 0);
        result = 31 * result + (tempoWorklogId != null ? tempoWorklogId.hashCode() : 0);
        result = 31 * result + (timeSpent != null ? timeSpent.hashCode() : 0);
        result = 31 * result + (timeSpentSeconds != null ? timeSpentSeconds.hashCode() : 0);
        result = 31 * result + (updater != null ? updater.hashCode() : 0);
        result = 31 * result + (worker != null ? worker.hashCode() : 0);
        return result;
    }
}
