
package com.senla.worklog.reminder.api.jira.v3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.annotation.Generated;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class IssueTypeV3 {
    private String iconUrl;
    private String name;

    @Override
    public String toString() {
        return "IssueType{" +
                "iconUrl='" + iconUrl + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssueTypeV3 issueType = (IssueTypeV3) o;

        if (iconUrl != null ? !iconUrl.equals(issueType.iconUrl) : issueType.iconUrl != null) return false;
        if (name != null ? !name.equals(issueType.name) : issueType.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = iconUrl != null ? iconUrl.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
