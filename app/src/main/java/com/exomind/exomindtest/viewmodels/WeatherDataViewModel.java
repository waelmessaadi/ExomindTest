package com.exomind.exomindtest.viewmodels;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exomind.exomindtest.models.ResponseWeather;
import com.exomind.exomindtest.repository.Repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WeatherDataViewModel extends ViewModel {

    Repository repository;

    private MutableLiveData<List<ResponseWeather>> mutableLiveData = new MutableLiveData<>();


    @ViewModelInject
    public WeatherDataViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<List<ResponseWeather>> getMutableLiveData() {
        return mutableLiveData;
    }

    public void getWeatherData(String city) {
        List<ResponseWeather> list = new ArrayList<>();
        repository.weatherData(city).subscribeOn(Schedulers.io()).map(responseWeather -> {
                    list.clear();
                    list.add(responseWeather);
                    return list;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> mutableLiveData.setValue(result),
                        error-> Log.e("TAG", "error getting users: " + error.getMessage()));
    }
}
