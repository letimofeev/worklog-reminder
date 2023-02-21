
package com.senla.worklog.reminder.api.v4.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.annotation.Generated;
import java.time.LocalDateTime;

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

    public Object getAttributes() {
        return attributes;
    }

    public WorklogV4 setAttributes(Object attributes) {
        this.attributes = attributes;
        return this;
    }

    public Long getBillableSeconds() {
        return billableSeconds;
    }

    public WorklogV4 setBillableSeconds(Long billableSeconds) {
        this.billableSeconds = billableSeconds;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public WorklogV4 setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public WorklogV4 setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public WorklogV4 setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public IssueV4 getIssue() {
        return issue;
    }

    public WorklogV4 setIssue(IssueV4 issue) {
        this.issue = issue;
        return this;
    }

    public Long getOriginId() {
        return originId;
    }

    public WorklogV4 setOriginId(Long originId) {
        this.originId = originId;
        return this;
    }

    public Long getOriginTaskId() {
        return originTaskId;
    }

    public WorklogV4 setOriginTaskId(Long originTaskId) {
        this.originTaskId = originTaskId;
        return this;
    }

    public LocalDateTime getStarted() {
        return started;
    }

    public WorklogV4 setStarted(LocalDateTime started) {
        this.started = started;
        return this;
    }

    public Long getTempoWorklogId() {
        return tempoWorklogId;
    }

    public WorklogV4 setTempoWorklogId(Long tempoWorklogId) {
        this.tempoWorklogId = tempoWorklogId;
        return this;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public WorklogV4 setTimeSpent(String timeSpent) {
        this.timeSpent = timeSpent;
        return this;
    }

    public Long getTimeSpentSeconds() {
        return timeSpentSeconds;
    }

    public WorklogV4 setTimeSpentSeconds(Long timeSpentSeconds) {
        this.timeSpentSeconds = timeSpentSeconds;
        return this;
    }

    public String getUpdater() {
        return updater;
    }

    public WorklogV4 setUpdater(String updater) {
        this.updater = updater;
        return this;
    }

    public String getWorker() {
        return worker;
    }

    public WorklogV4 setWorker(String worker) {
        this.worker = worker;
        return this;
    }

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
