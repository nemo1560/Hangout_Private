package com.example.nemo1.hangout_private.Instance;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nemo1 on 11/25/2018.
 */
//Create Retrofit object
public class InstanceRetrofitAPIWeather {
    private static Retrofit instance = null;

    public static Retrofit RetrofitInstance() {
        if (instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl("http://api.apixu.com/v1")
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }
}
