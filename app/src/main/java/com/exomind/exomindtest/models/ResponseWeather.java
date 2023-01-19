package com.exomind.exomindtest.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseWeather {

    @SerializedName("weather")
    @Expose
    private List<WeatherModel> weather = null;
    @SerializedName("main")
    @Expose
    private Main main;

    public List<WeatherModel> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherModel> weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

}
