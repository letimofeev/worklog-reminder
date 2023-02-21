
package com.senla.worklog.reminder.api.v4.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.annotation.Generated;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class IssueV4 {

    private List<Object> components;
    private String iconUrl;
    private Long id;
    private Boolean internalIssue;
    private String issueStatus;
    private String issueType;
    private String key;
    private String parentKey;
    private Long projectId;
    private String projectKey;
    private String reporterKey;
    private String summary;
    private List<Object> versions;

    @Override
    public String toString() {
        return "Issue{" +
                "components=" + components +
                ", iconUrl='" + iconUrl + '\'' +
                ", id=" + id +
                ", internalIssue=" + internalIssue +
                ", issueStatus='" + issueStatus + '\'' +
                ", issueType='" + issueType + '\'' +
                ", key='" + key + '\'' +
                ", parentKey='" + parentKey + '\'' +
                ", projectId=" + projectId +
                ", projectKey='" + projectKey + '\'' +
                ", reporterKey='" + reporterKey + '\'' +
                ", summary='" + summary + '\'' +
                ", versions=" + versions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssueV4 issue = (IssueV4) o;

        if (components != null ? !components.equals(issue.components) : issue.components != null) return false;
        if (iconUrl != null ? !iconUrl.equals(issue.iconUrl) : issue.iconUrl != null) return false;
        if (id != null ? !id.equals(issue.id) : issue.id != null) return false;
        if (internalIssue != null ? !internalIssue.equals(issue.internalIssue) : issue.internalIssue != null)
            return false;
        if (issueStatus != null ? !issueStatus.equals(issue.issueStatus) : issue.issueStatus != null) return false;
        if (issueType != null ? !issueType.equals(issue.issueType) : issue.issueType != null) return false;
        if (key != null ? !key.equals(issue.key) : issue.key != null) return false;
        if (parentKey != null ? !parentKey.equals(issue.parentKey) : issue.parentKey != null) return false;
        if (projectId != null ? !projectId.equals(issue.projectId) : issue.projectId != null) return false;
        if (projectKey != null ? !projectKey.equals(issue.projectKey) : issue.projectKey != null) return false;
        if (reporterKey != null ? !reporterKey.equals(issue.reporterKey) : issue.reporterKey != null) return false;
        if (summary != null ? !summary.equals(issue.summary) : issue.summary != null) return false;
        if (versions != null ? !versions.equals(issue.versions) : issue.versions != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = components != null ? components.hashCode() : 0;
        result = 31 * result + (iconUrl != null ? iconUrl.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (internalIssue != null ? internalIssue.hashCode() : 0);
        result = 31 * result + (issueStatus != null ? issueStatus.hashCode() : 0);
        result = 31 * result + (issueType != null ? issueType.hashCode() : 0);
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (parentKey != null ? parentKey.hashCode() : 0);
        result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
        result = 31 * result + (projectKey != null ? projectKey.hashCode() : 0);
        result = 31 * result + (reporterKey != null ? reporterKey.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (versions != null ? versions.hashCode() : 0);
        return result;
    }
}
