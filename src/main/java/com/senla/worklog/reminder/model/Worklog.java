
package com.senla.worklog.reminder.model;

import java.util.List;
import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Worklog {

    private Author author;
    private String comment;
    private String dateCreated;
    private String dateStarted;
    private String dateUpdated;
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

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(String dateStarted) {
        this.dateStarted = dateStarted;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public Long getJiraWorklogId() {
        return jiraWorklogId;
    }

    public void setJiraWorklogId(Long jiraWorklogId) {
        this.jiraWorklogId = jiraWorklogId;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public Long getTimeSpentSeconds() {
        return timeSpentSeconds;
    }

    public void setTimeSpentSeconds(Long timeSpentSeconds) {
        this.timeSpentSeconds = timeSpentSeconds;
    }

    public List<Object> getWorkAttributeValues() {
        return workAttributeValues;
    }

    public void setWorkAttributeValues(List<Object> workAttributeValues) {
        this.workAttributeValues = workAttributeValues;
    }

    public List<Object> getWorklogAttributes() {
        return worklogAttributes;
    }

    public void setWorklogAttributes(List<Object> worklogAttributes) {
        this.worklogAttributes = worklogAttributes;
    }

    @Override
    public String toString() {
        return "Worklog{" +
                "author=" + author +
                ", comment='" + comment + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", dateStarted='" + dateStarted + '\'' +
                ", dateUpdated='" + dateUpdated + '\'' +
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

        Worklog worklog = (Worklog) o;

        if (author != null ? !author.equals(worklog.author) : worklog.author != null) return false;
        if (comment != null ? !comment.equals(worklog.comment) : worklog.comment != null) return false;
        if (dateCreated != null ? !dateCreated.equals(worklog.dateCreated) : worklog.dateCreated != null) return false;
        if (dateStarted != null ? !dateStarted.equals(worklog.dateStarted) : worklog.dateStarted != null) return false;
        if (dateUpdated != null ? !dateUpdated.equals(worklog.dateUpdated) : worklog.dateUpdated != null) return false;
        if (id != null ? !id.equals(worklog.id) : worklog.id != null) return false;
        if (issue != null ? !issue.equals(worklog.issue) : worklog.issue != null) return false;
        if (jiraWorklogId != null ? !jiraWorklogId.equals(worklog.jiraWorklogId) : worklog.jiraWorklogId != null)
            return false;
        if (self != null ? !self.equals(worklog.self) : worklog.self != null) return false;
        if (timeSpentSeconds != null ? !timeSpentSeconds.equals(worklog.timeSpentSeconds) : worklog.timeSpentSeconds != null)
            return false;
        if (workAttributeValues != null ? !workAttributeValues.equals(worklog.workAttributeValues) : worklog.workAttributeValues != null)
            return false;
        if (worklogAttributes != null ? !worklogAttributes.equals(worklog.worklogAttributes) : worklog.worklogAttributes != null)
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
