package com.mroz.mateusz.weatherapplication.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mroz.mateusz.weatherapplication.weather.Weather;
import com.mroz.mateusz.weatherapplication.ui.CurentlyFragment;
import com.mroz.mateusz.weatherapplication.ui.DailyFragment;
import com.mroz.mateusz.weatherapplication.ui.HourFragment;
import com.mroz.mateusz.weatherapplication.ui.MainActivity;


public class FixedTabsPagerAdapter extends FragmentStatePagerAdapter {
    private Weather weatherForecast;

    public FixedTabsPagerAdapter(FragmentManager fm, Weather weatherForecast) {
        super(fm);
        this.weatherForecast = weatherForecast;
    }

    public FixedTabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle mBundle = new Bundle();
        switch(position) {
            case 0:
                if(weatherForecast != null) {
                    mBundle.putParcelableArray(MainActivity.HOURLY_FORECAST, weatherForecast.getHourlyWeather());
                    HourFragment hourFragment = new HourFragment();
                    hourFragment.setArguments(mBundle);

                    return hourFragment;
                }
                else return new HourFragment();
            case 1:
                if(weatherForecast != null) {
                    mBundle.putParcelable(MainActivity.CURENTLY_FORECAST, weatherForecast.getCurrentWeather());
                    mBundle.putParcelableArray(MainActivity.HOURLY_FORECAST, weatherForecast.getHourlyWeather());
                    CurentlyFragment curentlyFragment = new CurentlyFragment();
                    curentlyFragment.setArguments(mBundle);

                    return curentlyFragment;
                }
                else return new CurentlyFragment();

            case 2:
                if(weatherForecast != null) {
                    mBundle.putParcelableArray(MainActivity.DAILY_FORECAST, weatherForecast.getDailyWeather());
                    DailyFragment dailyFragment = new DailyFragment();
                    dailyFragment.setArguments(mBundle);

                    return dailyFragment;
                }
                else return new DailyFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Hourly";
            case 1:
                return "Currently";
            case 2:
                return "Daily";
        }
        return null;
    }

    @Override
    public int getItemPosition(Object object) {
        return FixedTabsPagerAdapter.POSITION_NONE;
    }
}
