package com.mroz.mateusz.weatherapplication.Weather;


import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HourlyWeather implements Parcelable {
    private long mTime;
    private double mTemperature;
    private int mDayOrNight;
    private String mDescription;
    private String mIcon;

    public HourlyWeather(long time, double temperature, int dayOrNight, String description,
                         String icon) {
        mTime = time;
        mTemperature = temperature;
        mDayOrNight = dayOrNight;
        mDescription = description;
        mIcon = icon;
    }

    public HourlyWeather(long time, double temperature, String icon, int dayOrNight) {
        mTime = time;
        mTemperature = temperature;
        mIcon = icon;
        mDayOrNight = dayOrNight;
    }

    public HourlyWeather() {}

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public int getTemperature() {
        return (int) Math.round(mTemperature);
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public int getDayOrNight() {
        return mDayOrNight;
    }

    public void setDayOrNight(int dayOrNight) {
        mDayOrNight = dayOrNight;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public int getIconId() {
        return Weather.getIconId(mIcon, mDayOrNight);
    }

    public String getHour() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date hour = new Date(mTime*1000);
        return formatter.format(hour);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mTime);
        dest.writeDouble(mTemperature);
        dest.writeInt(mDayOrNight);
        dest.writeString(mDescription);
        dest.writeString(mIcon);
    }

    private HourlyWeather(Parcel in) {
        mTime = in.readLong();
        mTemperature = in.readDouble();
        mDayOrNight = in.readInt();
        mDescription = in.readString();
        mIcon = in.readString();
    }

    public static final Creator<HourlyWeather> CREATOR = new Creator<HourlyWeather>() {
        @Override
        public HourlyWeather createFromParcel(Parcel source) {
            return new HourlyWeather(source);
        }

        @Override
        public HourlyWeather[] newArray(int size) {
            return new HourlyWeather[size];
        }
    };

}
