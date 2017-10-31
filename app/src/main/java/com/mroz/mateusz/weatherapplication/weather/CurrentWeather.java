package com.mroz.mateusz.weatherapplication.weather;


import android.os.Parcel;
import android.os.Parcelable;

public class CurrentWeather implements Parcelable{
    private String mLocation;
    private String mIcon;
    private double mTemperature;
    private String mDescription;
    private double mFeelsTemperarure;
    private double mWind;
    private double mHumidity;
    private double mPressure;
    private int mDayOrNight;

    public CurrentWeather() {}

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public int getTemperature() {
        return (int) Math.round(mTemperature);
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getFeelsTemperarure() {
        return (int) Math.round(mFeelsTemperarure);
    }

    public void setFeelsTemperarure(double feelsTemperarure) {
        mFeelsTemperarure = feelsTemperarure;
    }

    public int getWind() {
        return (int) Math.round(mWind);
    }

    public void setWind(double wind) {
        mWind = wind;
    }

    public int getHumidity() {
        return (int) Math.round(mHumidity);
    }

    public void setHumidity(int humidity) {
        mHumidity = humidity;
    }

    public int getPressure() {
        return (int) Math.round(mPressure);
    }

    public void setPressure(double pressure) {
        mPressure = pressure;
    }

    public int getDayOrNight() {
        return mDayOrNight;
    }

    public void setDayOrNight(int dayOrNight) {
        mDayOrNight = dayOrNight;
    }

    public int getIconId() {
        return Weather.getIconId(mIcon, mDayOrNight);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mLocation);
        dest.writeString(mIcon);
        dest.writeDouble(mTemperature);
        dest.writeString(mDescription);
        dest.writeDouble(mFeelsTemperarure);
        dest.writeDouble(mWind);
        dest.writeDouble(mHumidity);
        dest.writeDouble(mPressure);
        dest.writeInt(mDayOrNight);
    }

    protected CurrentWeather(Parcel in) {
        mLocation = in.readString();
        mIcon = in.readString();
        mTemperature = in.readDouble();
        mDescription = in.readString();
        mFeelsTemperarure = in.readDouble();
        mWind = in.readDouble();
        mHumidity = in.readDouble();
        mPressure = in.readDouble();
        mDayOrNight = in.readInt();
    }

    public static final Creator<CurrentWeather> CREATOR = new Creator<CurrentWeather>() {
        @Override
        public CurrentWeather createFromParcel(Parcel in) {
            return new CurrentWeather(in);
        }

        @Override
        public CurrentWeather[] newArray(int size) {
            return new CurrentWeather[size];
        }
    };
}
