
package com.senla.worklog.reminder.api.v3.model;

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
public class AuthorV3 {
    private String avatar;
    private String displayName;
    private String key;
    private String name;
    private String self;

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

        AuthorV3 author = (AuthorV3) o;

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
