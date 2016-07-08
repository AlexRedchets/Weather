package com.azvk.weather.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.azvk.weather.ChangeIcon;
import com.azvk.weather.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class HourlyForecastAdapter extends RecyclerView.Adapter<HourlyForecastAdapter.ViewHolder>{

    private List<com.azvk.weather.model_hourly.List> list;
    private Context context;
    private String TAG = HourlyForecastAdapter.class.getSimpleName();

    public HourlyForecastAdapter(Context context) {
        this.context = context;
    }

    public void updateAdapter(List<com.azvk.weather.model_hourly.List> lists){
        list = lists;
        notifyDataSetChanged();
        Log.i(TAG, "Adapter is updated");
    }

    public Context getContext(){
        return context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.custom_hourly_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        com.azvk.weather.model_hourly.List currentListData = list.get(position);

        String timeToShow = currentListData.getDt().toString();
        timeToShow = getTime(timeToShow);
        Integer currentTemp = (currentListData.getMain().getTemp()).intValue();
        String tempToShow = String.valueOf(currentTemp);
        ChangeIcon changeIcon = new ChangeIcon(currentListData.getWeather().get(0).getIcon());
        String image = changeIcon.getUrl();


        holder.hourlyTemp.setText(tempToShow + "°");
        holder.hourlyTime.setText(timeToShow);
        Picasso.with(context).load(image).into(holder.hourlyImage);

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public String getTime(String timeStamp){
        Date d = new Date(Long.parseLong(timeStamp) * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("h a");
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(d);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView hourlyTemp;
        TextView hourlyTime;
        ImageView hourlyImage;

        public ViewHolder(View itemView) {
            super(itemView);

            hourlyImage = (ImageView)itemView.findViewById(R.id.hourlyImage);
            hourlyTime = (TextView)itemView.findViewById(R.id.hourlyTime);
            hourlyTemp = (TextView)itemView.findViewById(R.id.hourlyTemp);
        }
    }

}
