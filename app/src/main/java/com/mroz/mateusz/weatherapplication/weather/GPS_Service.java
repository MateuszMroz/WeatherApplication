package com.mroz.mateusz.weatherapplication.weather;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

import com.mroz.mateusz.weatherapplication.ui.MainActivity;

public class GPS_Service extends Service {

    private LocationListener mLocationListener;
    private LocationManager mLocationManager;
    private static final int TIME_REFRESH= 1000*60;
    private static final int DISTANCE_REFRESH= 5;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Intent intent = new Intent(MainActivity.LOCATION_UPDATE);
                intent.putExtra(MainActivity.LATITUDE, location.getLatitude());
                intent.putExtra(MainActivity.LONGITUDE, location.getLongitude());
                sendBroadcast(intent);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        //noinspection MissingPermission
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TIME_REFRESH, DISTANCE_REFRESH, mLocationListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mLocationManager !=null) {
            //noinspection MissingPermission
            mLocationManager.removeUpdates(mLocationListener);
        }
    }
}
