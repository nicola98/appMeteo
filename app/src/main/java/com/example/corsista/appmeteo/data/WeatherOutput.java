package com.example.corsista.appmeteo.data;

import java.io.Serializable;
import java.util.List;

public class WeatherOutput implements Serializable {

    private String id;
    private String name;
    private List<ForecastOutput> weather;

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

    public List<ForecastOutput> getWeather() {
        return weather;
    }

    public void setWeather(List<ForecastOutput> weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "WeatherOutput{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", weather=" + weather +
                '}';
    }
}
