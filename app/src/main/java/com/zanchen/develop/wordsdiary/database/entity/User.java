package com.zanchen.develop.wordsdiary.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "users", indices = {@Index(value = {"user_name"},unique = true)})
public class User {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "user_name")
    public String user_name;

    @ColumnInfo(name = "user_password")
    public String user_password;

    public User(String user_name,String user_password) {
        this.user_name = user_name;
        this.user_password = user_password;
    }

    public int getUid() {
        return uid;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", user_name='" + user_name + '\'' +
                ", user_password='" + user_password + '\'' +
                '}';
    }
}
