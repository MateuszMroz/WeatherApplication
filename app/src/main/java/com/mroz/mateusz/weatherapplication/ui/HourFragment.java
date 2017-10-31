package com.mroz.mateusz.weatherapplication.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mroz.mateusz.weatherapplication.weather.GetHourWeather;
import com.mroz.mateusz.weatherapplication.R;
import com.mroz.mateusz.weatherapplication.weather.HourlyWeather;
import com.mroz.mateusz.weatherapplication.adapters.HourlyAdapter;

import java.util.ArrayList;

public class HourFragment extends Fragment {
    private ArrayList<HourlyWeather> mHourly;
    private HourlyWeather[] mFullHourlyWeather;
    private GetHourWeather mGetHourWeather;
    private Bundle mBundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_hour,container, false);

        TextView noData = (TextView)view.findViewById(R.id.no_data_hourly);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.list_hour_item);

        if(mBundle != null) {
            mGetHourWeather = new GetHourWeather(mFullHourlyWeather, mHourly, mBundle);
            HourlyAdapter adapter = new HourlyAdapter(mGetHourWeather.getHourly());
            mRecyclerView.setAdapter(adapter);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(inflater.getContext());
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setHasFixedSize(true);
        }
        else {
            noData.setVisibility(View.VISIBLE);
        }

        return view;
    }

}
