package com.azvk.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.azvk.weather.model_current.Model;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class WeatherFragment extends Fragment {

    private String temp;
    private String city;
    private String image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getWeatherValues();
    }

    public void getWeatherValues(){

        String latitude;
        String longitude;
        String mode = "json";
        String units = "metric";
        String key = "1fcad98585808b3ca4990830bc17bd16";

        Bundle mArgs = getArguments();
        latitude = String.valueOf(mArgs.getDouble("latitude"));
        longitude = String.valueOf(mArgs.getDouble("longitude"));

        WeatherClient client = ServiceGenerator.createService(WeatherClient.class);

        Call<Model> call = client.weather_current(latitude, longitude, mode, units, key);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if (response.isSuccessful()) {
                    temp = response.body().getMain().getTemp().toString();
                    city = response.body().getName();
                    image = response.body().getWeather().get(0).getIcon();
                    Log.i(TAG, "Getting WEATHER info from OPENWEATHER.com: SUCCESS");
                    createWeatherView(temp, city, image);
                } else {
                    Log.i(TAG, "Getting WEATHER info from OPENWEATHER.com: ERROR");
                }
            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e(TAG, "Unable connect to OPENWEATHER.com", t);
            }
        });
    }

    private void createWeatherView(String temp, String city, String image){

        TextView textDegree;
        TextView textCity;
        ImageView imageView;

        textDegree = (TextView)getActivity().findViewById(R.id.weatherTemp);
        textCity = (TextView)getActivity().findViewById(R.id.weatherCity);
        imageView = (ImageView)getActivity().findViewById(R.id.weatherImage);

        textDegree.setText(temp);
        textCity.setText(city);
        Picasso.with(getActivity()).load("http://openweathermap.org/img/w/" + image + ".png").into(imageView);

    }
}
