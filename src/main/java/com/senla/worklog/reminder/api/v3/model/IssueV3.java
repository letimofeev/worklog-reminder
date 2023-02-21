
package com.senla.worklog.reminder.api.v3.model;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class IssueV3 {
    private Long id;
    private IssueTypeV3 issueType;
    private String key;
    private Long projectId;
    private Long remainingEstimateSeconds;
    private String self;
    private String summary;

    public Long getId() {
        return id;
    }

    public IssueV3 setId(Long id) {
        this.id = id;
        return this;
    }

    public IssueTypeV3 getIssueType() {
        return issueType;
    }

    public IssueV3 setIssueType(IssueTypeV3 issueType) {
        this.issueType = issueType;
        return this;
    }

    public String getKey() {
        return key;
    }

    public IssueV3 setKey(String key) {
        this.key = key;
        return this;
    }

    public Long getProjectId() {
        return projectId;
    }

    public IssueV3 setProjectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    public Long getRemainingEstimateSeconds() {
        return remainingEstimateSeconds;
    }

    public IssueV3 setRemainingEstimateSeconds(Long remainingEstimateSeconds) {
        this.remainingEstimateSeconds = remainingEstimateSeconds;
        return this;
    }

    public String getSelf() {
        return self;
    }

    public IssueV3 setSelf(String self) {
        this.self = self;
        return this;
    }

    public String getSummary() {
        return summary;
    }

    public IssueV3 setSummary(String summary) {
        this.summary = summary;
        return this;
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

        IssueV3 issue = (IssueV3) o;

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
