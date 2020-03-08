package com.tunan.json.role;

/**
 * @description:
 * @author: tunan
 * @create: 2020-03-07 10:43
 * @since: 1.0.0
 **/
public class Column {

    private String key;
    private String header;
    private String width;
    private String allowSort;
    private String hidden;


    @Override
    public String toString() {
        return "Column{" +
                "key='" + key + '\'' +
                ", header='" + header + '\'' +
                ", width='" + width + '\'' +
                ", allowSort='" + allowSort + '\'' +
                ", hidden='" + hidden + '\'' +
                '}';
    }

    public Column() {
    }

    public Column(String key, String header, String width, String allowSort, String hidden) {
        this.key = key;
        this.header = header;
        this.width = width;
        this.allowSort = allowSort;
        this.hidden = hidden;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getAllowSort() {
        return allowSort;
    }

    public void setAllowSort(String allowSort) {
        this.allowSort = allowSort;
    }

    public String getHidden() {
        return hidden;
    }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }
}

