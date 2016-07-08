package com.azvk.weather.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.azvk.weather.ChangeIcon;
import com.azvk.weather.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ForecastAdapter extends BaseAdapter {

    private java.util.List<com.azvk.weather.model_forecast.List> list;
    private Context context;
    private LayoutInflater inflater;
    private String TAG = ForecastAdapter.class.getSimpleName();

    public ForecastAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    public void updateAdapter(java.util.List<com.azvk.weather.model_forecast.List> list){
        this.list = list;
        notifyDataSetChanged();
        Log.i(TAG, "Adapter is updated");
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
        SimpleDateFormat sdf = new SimpleDateFormat("MMM, d\nE");
        Integer currentTempDay = (currentListData.getTemp().getDay()).intValue();
        Integer currentTempNight = (currentListData.getTemp().getNight()).intValue();
        ChangeIcon changeIcon = new ChangeIcon(currentListData.getWeather().get(0).getIcon());
        String image = changeIcon.getUrl();


        mViewHolder.day.setText(sdf.format(d));
        mViewHolder.tempDay.setText(" " + String.valueOf(currentTempDay) + " °C");
        mViewHolder.tempNight.setText(" " + String.valueOf(currentTempNight) + " °C");
        Picasso.with(context).load(image).into(mViewHolder.forecastImage);

        return convertView;
    }

    private class ViewHolder{
        TextView tempDay;
        TextView tempNight;
        TextView day;
        ImageView forecastImage;

        public ViewHolder(View view) {
            tempDay = (TextView)view.findViewById(R.id.forecastDayTemp);
            tempNight = (TextView)view.findViewById(R.id.forecastNightTemp);
            day = (TextView)view.findViewById(R.id.forecastDay);
            forecastImage = (ImageView)view.findViewById(R.id.forecastImage);
        }
    }
}
