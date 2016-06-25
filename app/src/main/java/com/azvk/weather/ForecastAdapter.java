package com.azvk.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.azvk.weather.model_hourly.List;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ForecastAdapter extends BaseAdapter {

    private java.util.List<com.azvk.weather.model_forecast.List> list;
    private Context context;
    private LayoutInflater inflater;

    public ForecastAdapter (Context context) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    public void updateAdapter(java.util.List<com.azvk.weather.model_forecast.List> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public com.azvk.weather.model_forecast.List getItem(int position) {
        return list != null ? list.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return list != null ? list.get(position).hashCode() : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mViewHolder;

        if(convertView==null){
            convertView = inflater.inflate(R.layout.forecast_list_item, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        }
        else{
            mViewHolder = (ViewHolder)convertView.getTag();
        }

        com.azvk.weather.model_forecast.List currentListData = getItem(position);
        String timeToShow = currentListData.getDt().toString();
        Date d = new Date(Long.parseLong(timeToShow) * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        Integer currentTemp = (currentListData.getTemp().getDay()).intValue();


        mViewHolder.day.setText(sdf.format(d));
        mViewHolder.temp.setText(currentTemp.toString());
        Picasso.with(context).load("http://openweathermap.org/img/w/" + currentListData.getWeather().get(0).getIcon() + ".png").into(mViewHolder.forecastImage);

        return convertView;
    }

    private class ViewHolder{
        TextView temp;
        TextView day;
        ImageView forecastImage;

        public ViewHolder(View view) {
            temp = (TextView)view.findViewById(R.id.forecastTemp);
            day = (TextView)view.findViewById(R.id.forecastDay);
            forecastImage = (ImageView)view.findViewById(R.id.forecastImage);
        }
    }
}
