
package com.senla.worklog.reminder.model;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Issue {
    private Long id;
    private IssueType issueType;
    private String key;
    private Long projectId;
    private Long remainingEstimateSeconds;
    private String self;
    private String summary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public void setIssueType(IssueType issueType) {
        this.issueType = issueType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getRemainingEstimateSeconds() {
        return remainingEstimateSeconds;
    }

    public void setRemainingEstimateSeconds(Long remainingEstimateSeconds) {
        this.remainingEstimateSeconds = remainingEstimateSeconds;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", issueType=" + issueType +
                ", key='" + key + '\'' +
                ", projectId=" + projectId +
                ", remainingEstimateSeconds=" + remainingEstimateSeconds +
                ", self='" + self + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Issue issue = (Issue) o;

        if (id != null ? !id.equals(issue.id) : issue.id != null) return false;
        if (issueType != null ? !issueType.equals(issue.issueType) : issue.issueType != null) return false;
        if (key != null ? !key.equals(issue.key) : issue.key != null) return false;
        if (projectId != null ? !projectId.equals(issue.projectId) : issue.projectId != null) return false;
        if (remainingEstimateSeconds != null ? !remainingEstimateSeconds.equals(issue.remainingEstimateSeconds) : issue.remainingEstimateSeconds != null)
            return false;
        if (self != null ? !self.equals(issue.self) : issue.self != null) return false;
        if (summary != null ? !summary.equals(issue.summary) : issue.summary != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (issueType != null ? issueType.hashCode() : 0);
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
        result = 31 * result + (remainingEstimateSeconds != null ? remainingEstimateSeconds.hashCode() : 0);
        result = 31 * result + (self != null ? self.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        return result;
    }
}
