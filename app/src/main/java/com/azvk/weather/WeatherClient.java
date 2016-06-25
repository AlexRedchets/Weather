package com.azvk.weather;

import com.azvk.weather.model_hourly.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherClient {

    //hourly
    @GET("forecast/weather?")
    Call<Model> weather_hourly(
           @Query("lat") String latitude,
           @Query("lon") String longitude,
           @Query("mode") String mode,
           @Query("units") String units,
           @Query("cnt") int cnt,
           @Query("APPID") String key
    );
    //current
    @GET("weather?")
    Call<com.azvk.weather.model_current.Model> weather_current(
            @Query("lat") String latitude,
            @Query("lon") String longitude,
            @Query("mode") String mode,
            @Query("units") String units,
            @Query("APPID") String key
    );
    //forecast
    @GET("forecast/daily?")
    Call<com.azvk.weather.model_forecast.Model> weather_forecast(
            @Query("lat") String latitude,
            @Query("lon") String longitude,
            @Query("mode") String mode,
            @Query("units") String units,
            @Query("APPID") String key
    );
}