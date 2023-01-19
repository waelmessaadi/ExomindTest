package com.exomind.exomindtest.repository;

import com.exomind.exomindtest.commons.CommonKeys;
import com.exomind.exomindtest.interfaces.ApiServices;
import com.exomind.exomindtest.models.ResponseWeather;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class Repository {

    private ApiServices apiServices;

    @Inject
    public Repository(ApiServices apiServices) {
        this.apiServices=apiServices;
    }

    // Response apis
    public Observable<ResponseWeather> weatherData(String city) {
        return apiServices.getWeatherdata(city, CommonKeys.apiKey);
    }
}
