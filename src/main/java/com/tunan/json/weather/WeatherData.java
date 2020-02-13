package com.tunan.json.weather;

/**
 * @description:
 * @author: tunan
 * @create: 2020-02-10 10:12
 * @since: 1.0.0
 **/
public class WeatherData {

    private String date;
    private String dayPictureUrl;
    private String nightPictureUrl;
    private String weather;
    private String wind;
    private String temperature;

    public WeatherData() {
    }

    public WeatherData(String date, String dayPictureUrl, String nightPictureUrl, String weather, String wind, String temperature) {
        this.date = date;
        this.dayPictureUrl = dayPictureUrl;
        this.nightPictureUrl = nightPictureUrl;
        this.weather = weather;
        this.wind = wind;
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return  date+"\t"+
                dayPictureUrl+"\t"+
                nightPictureUrl+"\t"+
                weather+"\t"+
                wind+"\t"+
                temperature;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayPictureUrl() {
        return dayPictureUrl;
    }

    public void setDayPictureUrl(String dayPictureUrl) {
        this.dayPictureUrl = dayPictureUrl;
    }

    public String getNightPictureUrl() {
        return nightPictureUrl;
    }

    public void setNightPictureUrl(String nightPictureUrl) {
        this.nightPictureUrl = nightPictureUrl;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}

