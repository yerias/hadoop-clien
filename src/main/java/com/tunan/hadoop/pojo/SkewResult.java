package com.tunan.hadoop.pojo;

public class SkewResult {
    String id;
    String name;
    String domain;

    public SkewResult() {
    }

    public SkewResult(String id, String name, String domain) {
        this.id = id;
        this.name = name;
        this.domain = domain;
    }

    @Override
    public String toString() {
        return id +"\t"+name+"\t"+domain;
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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
