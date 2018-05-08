package com.example.riyagarg.aitweather.network;

import com.example.riyagarg.aitweather.data.WeatherResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by riyagarg on 5/2/18.
 */

public interface WeatherAPI {
    @GET("data/2.5/weather")
    Call<WeatherResult> getWeatherDetails(@Query("q") String city,
                                          @Query("units") String units,
                                          @Query("appid") String appid);
}
