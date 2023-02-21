package com.senla.worklog.reminder.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Issue {
    private Long id;
    private String key;
    private String issueType;
    private Long projectId;
    private String summary;

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", issueType='" + issueType + '\'' +
                ", projectId=" + projectId +
                ", summary='" + summary + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Issue issue = (Issue) o;

        if (!Objects.equals(id, issue.id)) return false;
        if (!Objects.equals(key, issue.key)) return false;
        if (!Objects.equals(issueType, issue.issueType)) return false;
        if (!Objects.equals(projectId, issue.projectId)) return false;
        return Objects.equals(summary, issue.summary);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (issueType != null ? issueType.hashCode() : 0);
        result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        return result;
    }
}
