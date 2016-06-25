package com.azvk.weather;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.azvk.weather.model_hourly.Model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastFragment extends Fragment {

    private static final String TAG = ForecastFragment.class.getSimpleName();

    public ForecastFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forecast, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getForecastValues();
    }

    private void getForecastValues() {

        String latitude;
        String longitude;
        String mode = "json";
        String units = "metric";
        String key = "1fcad98585808b3ca4990830bc17bd16";

        Bundle mArgs = getArguments();
        latitude = String.valueOf(mArgs.getDouble("latitude"));
        longitude = String.valueOf(mArgs.getDouble("longitude"));

        final ForecastAdapter forecastAdapter = new ForecastAdapter(getActivity());

        final ListView mListView = (ListView)getActivity().findViewById(R.id.forecastList);
        mListView.setAdapter(forecastAdapter);

        WeatherClient client = ServiceGenerator.createService(WeatherClient.class);

        Call<com.azvk.weather.model_forecast.Model> call = client.weather_forecast(latitude, longitude, mode, units, key);
        call.enqueue(new Callback<com.azvk.weather.model_forecast.Model>() {
            @Override
            public void onResponse(Call<com.azvk.weather.model_forecast.Model> call, Response<com.azvk.weather.model_forecast.Model> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Getting FORECAST info from OPENWEATHER.com: SUCCESS");
                    forecastAdapter.updateAdapter(response.body().getList());
                    Log.i(TAG, "Getting FORECAST info from OPENWEATHER.com: SUCCESS");
                } else {
                    Log.i(TAG, "Getting FORECAST info from OPENWEATHER.com: ERROR");
                }
            }
            @Override
            public void onFailure(Call<com.azvk.weather.model_forecast.Model> call, Throwable t) {
                Log.e(TAG, "Unable connect to OPENWEATHER.com", t);
            }
        });
    }
}
