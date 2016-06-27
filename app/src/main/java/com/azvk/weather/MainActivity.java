package com.azvk.weather;

import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "Running");
        runFragments();
    }

    private void runFragments(){
        Double latitude;
        Double longitude;

        Bundle extras = getIntent().getExtras();

        latitude = extras.getDouble("latitude");
        longitude = extras.getDouble("longitude");
        //The key argument here must match that used in the other activity

        Bundle args = new Bundle();
        args.putDouble("latitude", latitude);
        args.putDouble("longitude", longitude);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        WeatherFragment weatherFragment = new WeatherFragment();
        ForecastFragment forecastFragment = new ForecastFragment();
        HourlyForecastFragment hourlyForecastFragment = new HourlyForecastFragment();
        weatherFragment.setArguments(args);
        forecastFragment.setArguments(args);
        hourlyForecastFragment.setArguments(args);
        fragmentTransaction.add(R.id.weatherContainer, weatherFragment);
        fragmentTransaction.add(R.id.forecastContainer, forecastFragment);
        fragmentTransaction.add(R.id.hourlyForecastContainer, hourlyForecastFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "OnResume");
        super.onResume();
        runFragments();
    }
}
