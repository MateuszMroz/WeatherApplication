package com.mroz.mateusz.weatherapplication.Weather;


import android.os.Parcel;
import android.os.Parcelable;

public class UpdateCity implements Parcelable{
    private String mCityName;
    private double mLatitude;
    private double mLongitude;

    public UpdateCity(){}

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCityName);
        dest.writeDouble(mLatitude);
        dest.writeDouble(mLongitude);
    }

    public UpdateCity(Parcel in) {
        mCityName = in.readString();
        mLatitude = in.readDouble();
        mLongitude = in.readDouble();
    }

    public static final Creator<UpdateCity> CREATOR = new Creator<UpdateCity>() {
        @Override
        public UpdateCity createFromParcel(Parcel in) {
            return new UpdateCity(in);
        }

        @Override
        public UpdateCity[] newArray(int size) {
            return new UpdateCity[size];
        }
    };

    @Override
    public String toString() {
        return mCityName;
    }
}
