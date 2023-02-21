
package com.senla.worklog.reminder.api.v3.model;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class IssueTypeV3 {
    private String iconUrl;
    private String name;

    public String getIconUrl() {
        return iconUrl;
    }

    public IssueTypeV3 setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
        return this;
    }

    public String getName() {
        return name;
    }

    public IssueTypeV3 setName(String name) {
        this.name = name;
        return this;
    }

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
