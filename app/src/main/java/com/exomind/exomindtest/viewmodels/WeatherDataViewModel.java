package com.exomind.exomindtest.viewmodels;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.exomind.exomindtest.models.ResponseWeather;
import com.exomind.exomindtest.repository.Repository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherDataViewModel extends ViewModel {

    Repository repository;


    private ResponseWeather dataWeather;

    List<ResponseWeather> listData = new ArrayList<>();


    @ViewModelInject
    public WeatherDataViewModel(Repository repository) {
        this.repository = repository;
    }

    public void getWeatherData(String city) {

        repository.weatherData(city).enqueue(new Callback<ResponseWeather>() {
            @Override
            public void onResponse(Call<ResponseWeather> call, Response<ResponseWeather> response) {
                dataWeather = response.body();

                listData.add(dataWeather);
            }

            @Override
            public void onFailure(Call<ResponseWeather> call, Throwable t) {

            }
        });
    }

    public List<ResponseWeather> getWeatherDataList() {

        return listData;
    }
}
