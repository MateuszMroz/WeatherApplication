package com.mroz.mateusz.weatherapplication.weather;


import android.os.Bundle;
import android.os.Parcelable;

import com.mroz.mateusz.weatherapplication.ui.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class GetHourWeather {
    private HourlyWeather[] mFullHourlyWeather;
    private ArrayList<HourlyWeather> mHourly;
    private Bundle mBundle;
    private long millis = System.currentTimeMillis();

    public GetHourWeather(HourlyWeather[] hourlyWeathers,
                          ArrayList<HourlyWeather> hourly,
                          Bundle bundle) {
        mFullHourlyWeather = hourlyWeathers;
        mHourly = hourly;
        mBundle = bundle;
    }

    public ArrayList<HourlyWeather> getPartHourlyWeather() {
        ArrayList<HourlyWeather> fullHourly = getHourly();
        ArrayList<HourlyWeather> partHourly = new ArrayList<>();

        for (int i = 0; i <fullHourly.size() ; i+=4) {
            if(partHourly.size()<5) {
                HourlyWeather hourlyWeather = new HourlyWeather(
                    fullHourly.get(i).getTime(),
                    fullHourly.get(i).getTemperature(),
                    fullHourly.get(i).getIcon(),
                    fullHourly.get(i).getDayOrNight()
                );
                partHourly.add(hourlyWeather);
            }
        }

        return partHourly;
    }

    public ArrayList<HourlyWeather> getHourly() {
        Parcelable[] parcelables = mBundle.getParcelableArray(MainActivity.HOURLY_FORECAST);
        mFullHourlyWeather = Arrays.copyOf(parcelables, parcelables.length, HourlyWeather[].class);
        mHourly = new ArrayList<HourlyWeather>();

        Date currentTime = new Date(millis);

        for (int i = 0; i < mFullHourlyWeather.length; i++) {
            Date time = new Date(mFullHourlyWeather[i].getTime() * 1000);
            if (time.after(currentTime)) {
                HourlyWeather hourlyWeather = new HourlyWeather(
                        mFullHourlyWeather[i].getTime(),
                        mFullHourlyWeather[i].getTemperature(),
                        mFullHourlyWeather[i].getDayOrNight(),
                        mFullHourlyWeather[i].getDescription(),
                        mFullHourlyWeather[i].getIcon()
                );
                mHourly.add(hourlyWeather);
            }
        }
        mHourly.subList(24, mHourly.size()).clear();

        return mHourly;
    }
}
