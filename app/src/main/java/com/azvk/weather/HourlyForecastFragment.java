package com.azvk.weather;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class HourlyForecastFragment extends Fragment {

    private Integer cnt = 9;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.hourly_forecast_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getHourlyValues();
    }

    public void getHourlyValues(){

        String latitude;
        String longitude;
        String mode = "json";
        String units = "metric";
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
                    createHourlyView(response.body());
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

    private void createHourlyView(com.azvk.weather.model_hourly.Model model){

        String timeToShow;

        ImageView hourImage1 = (ImageView)getActivity().findViewById(R.id.hourlyImage1);
        ImageView hourImage2 = (ImageView)getActivity().findViewById(R.id.hourlyImage2);
        ImageView hourImage3 = (ImageView)getActivity().findViewById(R.id.hourlyImage3);
        ImageView hourImage4 = (ImageView)getActivity().findViewById(R.id.hourlyImage4);
        ImageView hourImage5 = (ImageView)getActivity().findViewById(R.id.hourlyImage5);
        ImageView hourImage6 = (ImageView)getActivity().findViewById(R.id.hourlyImage6);
        ImageView hourImage7 = (ImageView)getActivity().findViewById(R.id.hourlyImage7);
        ImageView hourImage8 = (ImageView)getActivity().findViewById(R.id.hourlyImage8);
        ImageView hourImage9 = (ImageView)getActivity().findViewById(R.id.hourlyImage9);
        TextView hourlyTime1 = (TextView)getActivity().findViewById(R.id.hourlyTime1);
        TextView hourlyTime2 = (TextView)getActivity().findViewById(R.id.hourlyTime2);
        TextView hourlyTime3 = (TextView)getActivity().findViewById(R.id.hourlyTime3);
        TextView hourlyTime4 = (TextView)getActivity().findViewById(R.id.hourlyTime4);
        TextView hourlyTime5 = (TextView)getActivity().findViewById(R.id.hourlyTime5);
        TextView hourlyTime6 = (TextView)getActivity().findViewById(R.id.hourlyTime6);
        TextView hourlyTime7 = (TextView)getActivity().findViewById(R.id.hourlyTime7);
        TextView hourlyTime8 = (TextView)getActivity().findViewById(R.id.hourlyTime8);
        TextView hourlyTime9 = (TextView)getActivity().findViewById(R.id.hourlyTime9);
        TextView hourlyTemp1 = (TextView)getActivity().findViewById(R.id.hourlyTemp1);
        TextView hourlyTemp2 = (TextView)getActivity().findViewById(R.id.hourlyTemp2);
        TextView hourlyTemp3 = (TextView)getActivity().findViewById(R.id.hourlyTemp3);
        TextView hourlyTemp4 = (TextView)getActivity().findViewById(R.id.hourlyTemp4);
        TextView hourlyTemp5 = (TextView)getActivity().findViewById(R.id.hourlyTemp5);
        TextView hourlyTemp6 = (TextView)getActivity().findViewById(R.id.hourlyTemp6);
        TextView hourlyTemp7 = (TextView)getActivity().findViewById(R.id.hourlyTemp7);
        TextView hourlyTemp8 = (TextView)getActivity().findViewById(R.id.hourlyTemp8);
        TextView hourlyTemp9 = (TextView)getActivity().findViewById(R.id.hourlyTemp9);

        timeToShow = model.getList().get(0).getDt().toString();
        Picasso.with(getActivity()).load("http://openweathermap.org/img/w/" + model.getList().get(0).getWeather().get(0).getIcon() + ".png").into(hourImage1);
        hourlyTime1.setText(getTime(timeToShow));
        hourlyTemp1.setText(String.valueOf((model.getList().get(0).getMain().getTemp().intValue())));


        timeToShow = model.getList().get(1).getDt().toString();
        Picasso.with(getActivity()).load("http://openweathermap.org/img/w/" + model.getList().get(1).getWeather().get(0).getIcon() + ".png").into(hourImage2);
        hourlyTime2.setText(getTime(timeToShow));
        hourlyTemp2.setText(String.valueOf((model.getList().get(1).getMain().getTemp().intValue())));

        timeToShow = model.getList().get(2).getDt().toString();
        Picasso.with(getActivity()).load("http://openweathermap.org/img/w/" + model.getList().get(2).getWeather().get(0).getIcon() + ".png").into(hourImage3);
        hourlyTime3.setText(getTime(timeToShow));
        hourlyTemp3.setText(String.valueOf((model.getList().get(2).getMain().getTemp().intValue())));

        timeToShow = model.getList().get(3).getDt().toString();
        Picasso.with(getActivity()).load("http://openweathermap.org/img/w/" + model.getList().get(3).getWeather().get(0).getIcon() + ".png").into(hourImage4);
        hourlyTime4.setText(getTime(timeToShow));
        hourlyTemp4.setText(String.valueOf((model.getList().get(3).getMain().getTemp().intValue())));

        timeToShow = model.getList().get(4).getDt().toString();
        Picasso.with(getActivity()).load("http://openweathermap.org/img/w/" + model.getList().get(4).getWeather().get(0).getIcon() + ".png").into(hourImage5);
        hourlyTime5.setText(getTime(timeToShow));
        hourlyTemp5.setText(String.valueOf((model.getList().get(4).getMain().getTemp().intValue())));

        timeToShow = model.getList().get(5).getDt().toString();
        Picasso.with(getActivity()).load("http://openweathermap.org/img/w/" + model.getList().get(5).getWeather().get(0).getIcon() + ".png").into(hourImage6);
        hourlyTime6.setText(getTime(timeToShow));
        hourlyTemp6.setText(String.valueOf((model.getList().get(5).getMain().getTemp().intValue())));

        timeToShow = model.getList().get(6).getDt().toString();
        Picasso.with(getActivity()).load("http://openweathermap.org/img/w/" + model.getList().get(6).getWeather().get(0).getIcon() + ".png").into(hourImage7);
        hourlyTime7.setText(getTime(timeToShow));
        hourlyTemp7.setText(String.valueOf((model.getList().get(6).getMain().getTemp().intValue())));

        timeToShow = model.getList().get(7).getDt().toString();
        Picasso.with(getActivity()).load("http://openweathermap.org/img/w/" + model.getList().get(7).getWeather().get(0).getIcon() + ".png").into(hourImage8);
        hourlyTime8.setText(getTime(timeToShow));
        hourlyTemp8.setText(String.valueOf((model.getList().get(7).getMain().getTemp().intValue())));

        timeToShow = model.getList().get(8).getDt().toString();
        Picasso.with(getActivity()).load("http://openweathermap.org/img/w/" + model.getList().get(8).getWeather().get(0).getIcon() + ".png").into(hourImage9);
        hourlyTime9.setText(getTime(timeToShow));
        hourlyTemp9.setText(String.valueOf((model.getList().get(8).getMain().getTemp().intValue())));

    }

    public String getTime(String timeStamp){
        Date d = new Date(Long.parseLong(timeStamp) * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("h a");
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(d);
    }
}