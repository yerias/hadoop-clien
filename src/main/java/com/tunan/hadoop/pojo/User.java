package com.tunan.hadoop.pojo;

public class User {
    String id;
    String name;

    @Override
    public String toString() {
        return id+"\t"+name;
    }

    public User() {
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}