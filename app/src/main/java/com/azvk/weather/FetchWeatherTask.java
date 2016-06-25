/*package com.azvk.weather;

import android.os.AsyncTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchWeatherTask extends AsyncTask<String, Void, Weather> {

    @Override
    protected Weather doInBackground(String... params) {

        WeatherClient client = ServiceGenerator.createService(WeatherClient.class);

        Call<Weather> call = client.weather(params[0], params[1], params[2], params[3]);
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

            }
        });


        return null;
    }
}
*/