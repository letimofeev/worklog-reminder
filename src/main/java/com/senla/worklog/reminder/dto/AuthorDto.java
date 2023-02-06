
package com.senla.worklog.reminder.dto;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AuthorDto {
    private String avatar;
    private String displayName;
    private String key;
    private String name;
    private String self;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    @Override
    public String toString() {
        return "Author{" +
                "avatar='" + avatar + '\'' +
                ", displayName='" + displayName + '\'' +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", self='" + self + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthorDto author = (AuthorDto) o;

        if (avatar != null ? !avatar.equals(author.avatar) : author.avatar != null) return false;
        if (displayName != null ? !displayName.equals(author.displayName) : author.displayName != null) return false;
        if (key != null ? !key.equals(author.key) : author.key != null) return false;
        if (name != null ? !name.equals(author.name) : author.name != null) return false;
        if (self != null ? !self.equals(author.self) : author.self != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = avatar != null ? avatar.hashCode() : 0;
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (self != null ? self.hashCode() : 0);
        return result;
    }
}
