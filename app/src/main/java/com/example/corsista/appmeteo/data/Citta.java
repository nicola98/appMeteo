package com.example.corsista.appmeteo.data;

/**
 * Created by Corsista on 30/03/2018.
 */

public class Citta {
    private String name;
    WeatherOutput weatherOutput;

    public WeatherOutput getWeatherOutput() {
        return weatherOutput;
    }

    public void setWeatherOutput(WeatherOutput weatherOutput) {
        this.weatherOutput = weatherOutput;
    }

    public Citta(String name){
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
