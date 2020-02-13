package com.tunan.json.weather;

import java.util.List;

/**
 * @description:
 * @author: tunan
 * @create: 2020-02-10 10:11
 * @since: 1.0.0
 **/
public class Result {

    private String currentCity;
    private List<WeatherData> weather_data;

    public Result() {
    }

    public Result(String currentCity, List<WeatherData> weather_data) {
        this.currentCity = currentCity;
        this.weather_data = weather_data;
    }

    @Override
    public String toString() {
        return currentCity+"\t"+weather_data;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public List<WeatherData> getWeather_data() {
        return weather_data;
    }

    public void setWeather_data(List<WeatherData> weather_data) {
        this.weather_data = weather_data;
    }
}

