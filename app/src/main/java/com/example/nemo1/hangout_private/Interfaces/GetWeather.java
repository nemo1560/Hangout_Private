package com.example.nemo1.hangout_private.Interfaces;

import com.example.nemo1.hangout_private.Entity.eWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nemo1 on 11/25/2018.
 */

public interface GetWeather {
    @GET("/current.json?key=a9919d781737410a90e72432180311")
    Call<eWeather> getAPIWeather(@Query("q")String location);
}
