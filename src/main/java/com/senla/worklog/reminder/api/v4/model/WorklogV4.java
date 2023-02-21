
package com.senla.worklog.reminder.api.v4.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.annotation.Generated;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class WorklogV4 {
    private Object attributes;
    private Long billableSeconds;
    private String comment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime dateCreated;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime dateUpdated;
    private IssueV4 issue;
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
        return "WorklogV4{" +
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

        WorklogV4 worklogV4 = (WorklogV4) o;

        if (attributes != null ? !attributes.equals(worklogV4.attributes) : worklogV4.attributes != null) return false;
        if (billableSeconds != null ? !billableSeconds.equals(worklogV4.billableSeconds) : worklogV4.billableSeconds != null)
            return false;
        if (comment != null ? !comment.equals(worklogV4.comment) : worklogV4.comment != null) return false;
        if (dateCreated != null ? !dateCreated.equals(worklogV4.dateCreated) : worklogV4.dateCreated != null)
            return false;
        if (dateUpdated != null ? !dateUpdated.equals(worklogV4.dateUpdated) : worklogV4.dateUpdated != null)
            return false;
        if (issue != null ? !issue.equals(worklogV4.issue) : worklogV4.issue != null) return false;
        if (originId != null ? !originId.equals(worklogV4.originId) : worklogV4.originId != null) return false;
        if (originTaskId != null ? !originTaskId.equals(worklogV4.originTaskId) : worklogV4.originTaskId != null)
            return false;
        if (started != null ? !started.equals(worklogV4.started) : worklogV4.started != null) return false;
        if (tempoWorklogId != null ? !tempoWorklogId.equals(worklogV4.tempoWorklogId) : worklogV4.tempoWorklogId != null)
            return false;
        if (timeSpent != null ? !timeSpent.equals(worklogV4.timeSpent) : worklogV4.timeSpent != null) return false;
        if (timeSpentSeconds != null ? !timeSpentSeconds.equals(worklogV4.timeSpentSeconds) : worklogV4.timeSpentSeconds != null)
            return false;
        if (updater != null ? !updater.equals(worklogV4.updater) : worklogV4.updater != null) return false;
        if (worker != null ? !worker.equals(worklogV4.worker) : worklogV4.worker != null) return false;

        return true;
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
