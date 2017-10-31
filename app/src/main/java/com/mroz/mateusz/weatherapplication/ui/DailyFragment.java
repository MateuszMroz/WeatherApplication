package com.mroz.mateusz.weatherapplication.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mroz.mateusz.weatherapplication.R;
import com.mroz.mateusz.weatherapplication.weather.DailyWeather;
import com.mroz.mateusz.weatherapplication.adapters.DailyAdapter;

import java.util.Arrays;

public class DailyFragment extends Fragment {
    private DailyWeather[] mDailyWeathers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_daily,container, false);
        TextView noData = (TextView)view.findViewById(R.id.no_data_daily);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.list_day_item);
        Bundle mBundle = getArguments();

        if(mBundle != null) {
            Parcelable[] parcelables = mBundle.getParcelableArray(MainActivity.DAILY_FORECAST);
            mDailyWeathers = Arrays.copyOf(parcelables, parcelables.length, DailyWeather[].class);

            DailyAdapter dailyAdapter = new DailyAdapter(mDailyWeathers);
            mRecyclerView.setAdapter(dailyAdapter);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
            mRecyclerView.setLayoutManager(layoutManager);

            mRecyclerView.setHasFixedSize(true);
        }
        else {
            noData.setVisibility(View.VISIBLE);
        }
       return view;
    }

}
