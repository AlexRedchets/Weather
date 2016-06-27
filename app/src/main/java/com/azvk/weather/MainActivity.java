package com.azvk.weather;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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
        super.onResume();
        Log.i(TAG, "OnResume");
        runFragments();
    }
}
