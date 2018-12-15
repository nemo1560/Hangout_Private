package com.example.nemo1.hangout_private.Models;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.nemo1.hangout_private.Entity.eWeather;
import com.example.nemo1.hangout_private.Instance.InstanceRetrofitAPIWeather;
import com.example.nemo1.hangout_private.Interfaces.Chat;
import com.example.nemo1.hangout_private.Interfaces.GetWeather;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import retrofit2.Callback;
import retrofit2.Response;

public class WeatherModel implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private GoogleApiClient googleApiClient;
    private Location location;
    private LocationRequest locationRequest;
    private static final long UPDATE_INTERVAL = 5000;
    private static final long FASTEST_INTERVAL = 5000;
    private Context context;
    private Chat chat;

    public WeatherModel(Context context, Chat chat) {
        this.context = context;
        this.chat = chat;
        buildGoogleAPIClientRequest();
        buildLocationRequest();
        googleApiClient.connect();
        getGPS();
    }


    public void buildLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
    }

    public void buildGoogleAPIClientRequest() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location2 = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location2 != null){
            location = location2;
            String locationString = location.getAltitude()+","+location.getLongitude();
            getWeather(locationString);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        String locationString = location.getAltitude()+","+location.getLongitude();
        getWeather(locationString);
    }

    public void getGPS(){
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

    }

    public void getWeather(String attidute){
        final eWeather eWeather = new eWeather();
        GetWeather getWeather = InstanceRetrofitAPIWeather.RetrofitInstance().create(GetWeather.class);
        retrofit2.Call<eWeather>eWeatherCall = getWeather.getAPIWeather(attidute);
        eWeatherCall.enqueue(new Callback<eWeather>() {
            @Override
            public void onResponse(retrofit2.Call<eWeather> call, Response<eWeather> response) {
                eWeather.setCurrent(response.body().getCurrent());
                eWeather.setLocation(response.body().getLocation());
                chat.onGetWeather(eWeather.getCurrent().getTemp_c());
            }

            @Override
            public void onFailure(retrofit2.Call<eWeather> call, Throwable t) {

            }
        });
    }
}
