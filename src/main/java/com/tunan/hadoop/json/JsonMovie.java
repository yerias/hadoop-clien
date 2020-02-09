package com.tunan.hadoop.json;

/**
 * @description:
 * @author: tunan
 * @create: 2020-02-09 18:56
 * @since: 1.0.0
 **/
public class JsonMovie {

    private String movie;
    private String rate;
    private String time;
    private String userId;

    @Override
    public String toString() {
        return movie+"\t"+rate+"\t"+time+"\t"+userId ;
    }

    public JsonMovie() {
    }

    public JsonMovie(String movie, String rate, String time, String userId) {
        this.movie = movie;
        this.rate = rate;
        this.time = time;
        this.userId = userId;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

