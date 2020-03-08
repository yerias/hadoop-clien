package com.tunan.json.user;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: tunan
 * @create: 2020-03-07 09:55
 * @since: 1.0.0
 **/
public class UserGroup {

    private String name;
    private List<User> users= new ArrayList<User>();

    public UserGroup() {
    }

    public UserGroup(String name, List<User> users) {
        this.name = name;
        this.users = users;
    }

    @Override
    public String toString() {
        return "UserGroup{" +
                "name='" + name + '\'' +
                ", users=" + users +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}

