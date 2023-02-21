
package com.senla.worklog.reminder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Worklog {
    private Author author;
    private String comment;
    private LocalDateTime dateCreated;
    private LocalDateTime dateStarted;
    private LocalDateTime dateUpdated;
    private Long id;
    private Issue issue;
    private Long jiraWorklogId;
    private String self;
    private Long timeSpentSeconds;
    private List<Object> workAttributeValues;
    private List<Object> worklogAttributes;

    public Author getAuthor() {
        return author;
    }

    public Worklog setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Worklog setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public Worklog setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public LocalDateTime getDateStarted() {
        return dateStarted;
    }

    public Worklog setDateStarted(LocalDateTime dateStarted) {
        this.dateStarted = dateStarted;
        return this;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public Worklog setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Worklog setId(Long id) {
        this.id = id;
        return this;
    }

    public Issue getIssue() {
        return issue;
    }

    public Worklog setIssue(Issue issue) {
        this.issue = issue;
        return this;
    }

    public Long getJiraWorklogId() {
        return jiraWorklogId;
    }

    public Worklog setJiraWorklogId(Long jiraWorklogId) {
        this.jiraWorklogId = jiraWorklogId;
        return this;
    }

    public String getSelf() {
        return self;
    }

    public Worklog setSelf(String self) {
        this.self = self;
        return this;
    }

    public Long getTimeSpentSeconds() {
        return timeSpentSeconds;
    }

    public Worklog setTimeSpentSeconds(Long timeSpentSeconds) {
        this.timeSpentSeconds = timeSpentSeconds;
        return this;
    }

    public List<Object> getWorkAttributeValues() {
        return workAttributeValues;
    }

    public Worklog setWorkAttributeValues(List<Object> workAttributeValues) {
        this.workAttributeValues = workAttributeValues;
        return this;
    }

    public List<Object> getWorklogAttributes() {
        return worklogAttributes;
    }

    public Worklog setWorklogAttributes(List<Object> worklogAttributes) {
        this.worklogAttributes = worklogAttributes;
        return this;
    }

    @Override
    public String toString() {
        return "WorklogDto{" +
                "author=" + author +
                ", comment='" + comment + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateStarted=" + dateStarted +
                ", dateUpdated=" + dateUpdated +
                ", id=" + id +
                ", issue=" + issue +
                ", jiraWorklogId=" + jiraWorklogId +
                ", self='" + self + '\'' +
                ", timeSpentSeconds=" + timeSpentSeconds +
                ", workAttributeValues=" + workAttributeValues +
                ", worklogAttributes=" + worklogAttributes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Worklog that = (Worklog) o;

        if (author != null ? !author.equals(that.author) : that.author != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (dateCreated != null ? !dateCreated.equals(that.dateCreated) : that.dateCreated != null) return false;
        if (dateStarted != null ? !dateStarted.equals(that.dateStarted) : that.dateStarted != null) return false;
        if (dateUpdated != null ? !dateUpdated.equals(that.dateUpdated) : that.dateUpdated != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (issue != null ? !issue.equals(that.issue) : that.issue != null) return false;
        if (jiraWorklogId != null ? !jiraWorklogId.equals(that.jiraWorklogId) : that.jiraWorklogId != null)
            return false;
        if (self != null ? !self.equals(that.self) : that.self != null) return false;
        if (timeSpentSeconds != null ? !timeSpentSeconds.equals(that.timeSpentSeconds) : that.timeSpentSeconds != null)
            return false;
        if (workAttributeValues != null ? !workAttributeValues.equals(that.workAttributeValues) : that.workAttributeValues != null)
            return false;
        if (worklogAttributes != null ? !worklogAttributes.equals(that.worklogAttributes) : that.worklogAttributes != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = author != null ? author.hashCode() : 0;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        result = 31 * result + (dateStarted != null ? dateStarted.hashCode() : 0);
        result = 31 * result + (dateUpdated != null ? dateUpdated.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (issue != null ? issue.hashCode() : 0);
        result = 31 * result + (jiraWorklogId != null ? jiraWorklogId.hashCode() : 0);
        result = 31 * result + (self != null ? self.hashCode() : 0);
        result = 31 * result + (timeSpentSeconds != null ? timeSpentSeconds.hashCode() : 0);
        result = 31 * result + (workAttributeValues != null ? workAttributeValues.hashCode() : 0);
        result = 31 * result + (worklogAttributes != null ? worklogAttributes.hashCode() : 0);
        return result;
    }
}
