package com.mroz.mateusz.weatherapplication.weather;


import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyWeather implements Parcelable{
    private long mDate;
    private double mMinTemperature;
    private double mMaxTemperature;
    private String mDesciption;
    private String mIcon;

    public DailyWeather() {}

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }

    public int getMinTemperature() {
        return (int) Math.round(mMinTemperature);
    }

    public void setMinTemperature(double minTemperature) {
        mMinTemperature = minTemperature;
    }

    public int getMaxTemperature() {
        return (int) Math.round(mMaxTemperature);
    }

    public void setMaxTemperature(double maxTemperature) {
        mMaxTemperature = maxTemperature;
    }

    public String getDesciption() {
        return mDesciption;
    }

    public void setDesciption(String desciption) {
        mDesciption = desciption;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public int getIconId() {
        return Weather.getIconId(mIcon, 1);
    }

    public String getDayName() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
        Date dateTime = new Date(mDate *1000);
        return dateFormat.format(dateTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mDate);
        dest.writeDouble(mMinTemperature);
        dest.writeDouble(mMaxTemperature);
        dest.writeString(mDesciption);
        dest.writeString(mIcon);
    }

    protected DailyWeather(Parcel in) {
        mDate = in.readLong();
        mMinTemperature = in.readDouble();
        mMaxTemperature = in.readDouble();
        mDesciption = in.readString();
        mIcon = in.readString();
    }

    public static final Creator<DailyWeather> CREATOR = new Creator<DailyWeather>() {
        @Override
        public DailyWeather createFromParcel(Parcel in) {
            return new DailyWeather(in);
        }

        @Override
        public DailyWeather[] newArray(int size) {
            return new DailyWeather[size];
        }
    };


}
