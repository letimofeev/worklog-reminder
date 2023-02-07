
package com.senla.worklog.reminder.model;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class IssueType {
    private String iconUrl;
    private String name;

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

        IssueType issueType = (IssueType) o;

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
