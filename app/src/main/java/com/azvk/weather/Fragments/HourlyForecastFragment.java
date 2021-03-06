package com.azvk.weather.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azvk.weather.Adapters.HourlyForecastAdapter;
import com.azvk.weather.R;
import com.azvk.weather.ServiceGenerator;
import com.azvk.weather.WeatherClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class HourlyForecastFragment extends Fragment {

    private HourlyForecastAdapter adapter;
    private List<com.azvk.weather.model_hourly.List> list;

    private final String KEY_RECYCLER_STATE = "recycler_state";
    private RecyclerView recyclerView;
    private static Bundle bundleRecyclerViewState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.hourly_forecast_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView(view);
        getHourlyValues();
    }

    private void getHourlyValues() {

        String latitude;
        String longitude;
        String mode = "json";
        String units = "metric";
        Integer cnt = 9;
        String key = "1fcad98585808b3ca4990830bc17bd16";

        Bundle mArgs = getArguments();
        latitude = String.valueOf(mArgs.getDouble("latitude"));
        longitude = String.valueOf(mArgs.getDouble("longitude"));

        //latitude = String.valueOf(52.0515);
        //longitude = String.valueOf(113.4712);

        WeatherClient client = ServiceGenerator.createService(WeatherClient.class);

        Call<com.azvk.weather.model_hourly.Model> call = client.weather_hourly(latitude, longitude, mode, units, cnt, key);
        call.enqueue(new Callback<com.azvk.weather.model_hourly.Model>() {
            @Override
            public void onResponse(Call<com.azvk.weather.model_hourly.Model> call, Response<com.azvk.weather.model_hourly.Model> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Getting HOURLY WEATHER info from OPENWEATHER.com: SUCCESS");
                    list = response.body().getList();
                    adapter.updateAdapter(list);
                } else {
                    Log.i(TAG, "Getting HOURLY WEATHER info from OPENWEATHER.com: ERROR");
                }
            }

            @Override
            public void onFailure(Call<com.azvk.weather.model_hourly.Model> call, Throwable t) {
                Log.e(TAG, "Unable connect to OPENWEATHER.com", t);
            }
        });
    }

    private void setupView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.hourlyWeather);
        //recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HourlyForecastAdapter(getActivity()) ;
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onPause()
    {
        super.onPause();

        // save RecyclerView state
        bundleRecyclerViewState = new Bundle();
        Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
        bundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        // restore RecyclerView state
        if (bundleRecyclerViewState != null) {
            Parcelable listState = bundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }
}