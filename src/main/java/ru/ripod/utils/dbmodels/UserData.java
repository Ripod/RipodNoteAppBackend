package ru.ripod.utils.dbmodels;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userdata")
public class UserData {
    @Id
    private long userId;
    private String name;

    public UserData() {
    }

    public UserData(long userId, String name){
        this.userId = userId;
        this.name = name;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                '}';
    }
}
