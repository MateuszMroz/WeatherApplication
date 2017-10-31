package com.mroz.mateusz.weatherapplication.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mroz.mateusz.weatherapplication.R;
import com.mroz.mateusz.weatherapplication.Weather.HourlyWeather;

import java.util.ArrayList;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.HourViewHolder> {
    private ArrayList<HourlyWeather> mHourlyWeather;

    public HourlyAdapter(ArrayList<HourlyWeather> hourlyWeather) {
        mHourlyWeather = hourlyWeather;
    }

    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hourly_view, parent, false);
        HourViewHolder viewHolder = new HourViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HourViewHolder holder, int position) {
            holder.bindHour(mHourlyWeather.get(position));
    }

    @Override
    public int getItemCount() {
        return mHourlyWeather.size();
    }

    public class HourViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIconWeather;
        public TextView mTemperature;
        public TextView mTime;
        public TextView mDescription;

        public HourViewHolder(View itemView) {
            super(itemView);
            mIconWeather = (ImageView) itemView.findViewById(R.id.icon_small);
            mTemperature = (TextView) itemView.findViewById(R.id.temperature_small);
            mTime = (TextView) itemView.findViewById(R.id.time_small);
            mDescription = (TextView) itemView.findViewById(R.id.description_weather);
        }

        public void bindHour(HourlyWeather hour) {
            mIconWeather.setImageResource(hour.getIconId());
            mTemperature.setText(hour.getTemperature() + " °C");
            mTime.setText(hour.getHour());
            mDescription.setText(hour.getDescription());
        }
    }
}
