package com.mroz.mateusz.weatherapplication.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mroz.mateusz.weatherapplication.R;
import com.mroz.mateusz.weatherapplication.weather.DailyWeather;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.DailyViewHolder> {
    private DailyWeather[] mDailyWeather;

    public DailyAdapter(DailyWeather[] dailyWeather) {
        mDailyWeather = dailyWeather;
    }

    @Override
    public DailyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.daily_view, parent, false);
        DailyViewHolder viewHolder = new DailyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DailyViewHolder holder, int position) {
        holder.bind(mDailyWeather[position], position);
    }

    @Override
    public int getItemCount() {
        return mDailyWeather.length;
    }


    public class DailyViewHolder extends RecyclerView.ViewHolder {
        public TextView mDate;
        public TextView mMinTemperature;
        public TextView mMaxTemperature;
        public TextView mDesciption;
        public ImageView mIcon;

        public DailyViewHolder(View itemView) {
            super(itemView);
            mDate = (TextView) itemView.findViewById(R.id.day_name);
            mMinTemperature = (TextView) itemView.findViewById(R.id.min_temp);
            mMaxTemperature = (TextView) itemView.findViewById(R.id.max_temp);
            mDesciption= (TextView) itemView.findViewById(R.id.weather_description);
            mIcon = (ImageView) itemView.findViewById(R.id.icon_weather);
        }

        public void bind(DailyWeather day, int position) {
            if(position == 0) {
                mDate.setText(R.string.today);
            }
            else {
                mDate.setText(day.getDayName());
            }

            mMinTemperature.setText(day.getMinTemperature() + "°C");
            mMaxTemperature.setText(day.getMaxTemperature() + "°C");
            mDesciption.setText(day.getDesciption());
            mIcon.setImageResource(day.getIconId());
        }
    }
}
