package com.exomind.exomindtest.interfaces;

import com.exomind.exomindtest.models.ResponseWeather;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {
    @GET("weather")
    Observable<ResponseWeather> getWeatherdata(@Query("q") String city, @Query("appid") String apiKey);

}
