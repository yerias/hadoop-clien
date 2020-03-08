package com.tunan.json.role;

import java.util.List;

/**
 * @description:
 * @author: tunan
 * @create: 2020-03-07 10:45
 * @since: 1.0.0
 **/
public class Query {

    private String id;
    private String key;
    private String tableName;
    private String className;
    private List<Column> column;

    @Override
    public String toString() {
        return "Query{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", tableName='" + tableName + '\'' +
                ", className='" + className + '\'' +
                ", column=" + column +
                '}';
    }

    public Query() {
    }

    public Query(String id, String key, String tableName, String className, List<Column> column) {
        this.id = id;
        this.key = key;
        this.tableName = tableName;
        this.className = className;
        this.column = column;
    }

    public List<Column> getColumn() {
        return column;
    }

    public void setColumn(List<Column> column) {
        this.column = column;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}

