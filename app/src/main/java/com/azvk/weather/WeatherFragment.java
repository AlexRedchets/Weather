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
import com.google.android.gms.vision.text.Text;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

class WeatherFragment extends Fragment {

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

    private void getWeatherValues(){

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
                    Integer temp = (response.body().getMain().getTemp()).intValue();
                    city = response.body().getName();
                    ChangeIcon changeIcon = new ChangeIcon(response.body().getWeather().get(0).getIcon());
                    Log.i(TAG, "Getting WEATHER info from OPENWEATHER.com: SUCCESS");
                    createWeatherView(response.body());
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

    private void createWeatherView(Model model){

        TextView textDegree;
        TextView textCity;
        TextView textCountry;
        TextView textHumidity;
        TextView textPressure;
        TextView textWind;
        TextView textDate;
        ImageView imageView;

        String temp;
        String city;
        String country;
        String humidity;
        String pressure;
        String wind;
        String image;

        textDegree = (TextView)getActivity().findViewById(R.id.weatherTemp);
        textCity = (TextView)getActivity().findViewById(R.id.weatherCity);
        textCountry = (TextView)getActivity().findViewById(R.id.weatherCountry);
        textHumidity = (TextView)getActivity().findViewById(R.id.weatherHumidity);
        textPressure = (TextView)getActivity().findViewById(R.id.weatherPressure);
        textWind = (TextView)getActivity().findViewById(R.id.weatherWind);
        textDate = (TextView)getActivity().findViewById(R.id.weatherDate);
        imageView = (ImageView)getActivity().findViewById(R.id.weatherImage);

        temp = String.valueOf((model.getMain().getTemp()).intValue());
        city = model.getName();
        country = model.getSys().getCountry();
        humidity = String.valueOf(model.getMain().getHumidity().intValue());
        pressure = String.valueOf(model.getMain().getPressure().intValue());
        wind = model.getWind().getSpeed().toString();

        ChangeIcon changeIcon = new ChangeIcon(model.getWeather().get(0).getIcon());
        image = changeIcon.getUrl();

        String timeToShow = model.getDt().toString();
        Date d = new Date(Long.parseLong(timeToShow) * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM, d \nh:m a");



        textDegree.setText(" " + temp + " °C");
        textCity.setText(city + ", ");
        textCountry.setText(country);
        textHumidity.setText(" " + humidity + " %");
        textPressure.setText(" " + pressure + " hpa");
        textWind.setText(" " + wind + " m/sec");
        textDate.setText(sdf.format(d));
        Picasso.with(getActivity()).load(image).into(imageView);

    }
}
