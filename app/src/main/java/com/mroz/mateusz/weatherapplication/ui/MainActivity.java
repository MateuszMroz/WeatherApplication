package com.mroz.mateusz.weatherapplication.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.eftimoff.viewpagertransformers.ZoomOutSlideTransformer;
import com.mroz.mateusz.weatherapplication.Weather.AlertDialogFragment;
import com.mroz.mateusz.weatherapplication.adapters.FixedTabsPagerAdapter;
import com.mroz.mateusz.weatherapplication.Weather.GPS_Service;
import com.mroz.mateusz.weatherapplication.R;
import com.mroz.mateusz.weatherapplication.Weather.SearchCity;
import com.mroz.mateusz.weatherapplication.Weather.CurrentWeather;
import com.mroz.mateusz.weatherapplication.Weather.DailyWeather;
import com.mroz.mateusz.weatherapplication.Weather.HourlyWeather;
import com.mroz.mateusz.weatherapplication.Weather.UpdateCity;
import com.mroz.mateusz.weatherapplication.Weather.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String HOURLY_FORECAST = "hourly_forecast";
    public static final String DAILY_FORECAST = "daily_forecast";
    public static final String CURENTLY_FORECAST = "curently_forecast";
    public static final String CITY = "city";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE_SEARCH_CITY = "latitude_search";
    public static final String LONGITUDE_SEARCH_CITY = "longitude_search";
    public static final String LOCATION_UPDATE = "location_update";
    public static final int LOCATION_CODE = 10;

    private FixedTabsPagerAdapter mFixedTabsPagerAdapter;
    private ViewPager mViewPager;
    private BroadcastReceiver mBroadcastReciever;
    private CheckBox gps;

    private double longitudeSearch;
    private double latitudeSearch;
    private String latitude;
    private String longitude;
    private double latitudeCityFind;
    private double longitudeCityFind;
    private boolean permisionOnOff;

    @Override
    protected void onResume() {
        super.onResume();
        if (mBroadcastReciever == null) {
            mBroadcastReciever = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    longitudeSearch = intent.getDoubleExtra(LONGITUDE, 0);
                    latitudeSearch = intent.getDoubleExtra(LATITUDE, 0);
                }
            };
        }

        registerReceiver(mBroadcastReciever, new IntentFilter(LOCATION_UPDATE));
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permisionOnOff = runtimePermissons();

        latitude = "50.08";
        longitude = "19.92";

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       // Log.v("TAAAAAG", "Wysyołanie onCreate");

        if (getIntent().hasExtra(LATITUDE_SEARCH_CITY)) {
            latitudeCityFind = getIntent().getDoubleExtra(LATITUDE_SEARCH_CITY, 0);
            longitudeCityFind = getIntent().getDoubleExtra(LONGITUDE_SEARCH_CITY, 0);
            new GetWeather().execute(String.valueOf(latitudeCityFind),
                    String.valueOf(longitudeCityFind));
        }
        else {
            new GetWeather().execute(latitude, longitude);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Intent intent = new Intent(getApplicationContext(), GPS_Service.class);
        stopService(intent);

        if (mBroadcastReciever != null) {
            unregisterReceiver(mBroadcastReciever);
        }
    }

    private boolean runtimePermissons() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_CODE);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case LOCATION_CODE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    enebleButtons();
                }
                else runtimePermissons();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem itemSwitch = menu.findItem(R.id.localization_on_off);
        itemSwitch.setActionView(R.layout.on_off_gps);
        gps = (CheckBox) menu.findItem(R.id.localization_on_off)
                .getActionView()
                .findViewById(R.id.checkBox2);

        if (!permisionOnOff) {
            enebleButtons();
        }

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                new GetWeather().execute(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        Drawable search = menu.findItem(R.id.search).getIcon();
        search.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.refresh_weather) {
            if (getIntent().hasExtra(LATITUDE_SEARCH_CITY)) {
                new GetWeather().execute(String.valueOf(latitudeCityFind),
                        String.valueOf(longitudeCityFind));
            }
            else if(gps.isChecked() && latitudeSearch != 0 && longitudeSearch != 0) {
                new GetWeather().execute(String.valueOf(latitudeSearch),
                        String.valueOf(longitudeSearch));
            }
            else {
                new GetWeather().execute(latitude, longitude);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void enebleButtons() {
        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (gps.isChecked()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(R.string.turn_on_gps);
                builder.setPositiveButton(R.string.yes_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), GPS_Service.class);
                        startService(intent);
                        if(latitudeSearch == 0 && longitudeSearch == 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle(R.string.warning);
                            builder.setMessage(R.string.no_gps_connection);
                            builder.setPositiveButton(R.string.ok_button,null);
                            AlertDialog alert = builder.create();
                            alert.show();
                            gps.setChecked(false);
                            gps.setButtonDrawable(R.drawable.ic_location_off_black_24dp);
                        }
                        else {
                            String latitudeSearchString = String.valueOf(latitudeSearch);
                            String longitudeSearchString = String.valueOf(longitudeSearch);

                            new GetWeather().execute(latitudeSearchString, longitudeSearchString);
                            gps.setChecked(true);
                            gps.setButtonDrawable(R.drawable.ic_location_on_black_24dp);
                        }
                    }
                });
                builder.setNegativeButton(R.string.no_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gps.setChecked(false);
                        gps.setButtonDrawable(R.drawable.ic_location_off_black_24dp);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
            if(!gps.isChecked()){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(R.string.turn_off_gps);
                builder.setPositiveButton(R.string.yes_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), GPS_Service.class);
                        stopService(intent);
                        gps.setChecked(false);
                        gps.setButtonDrawable(R.drawable.ic_location_off_black_24dp);
                    }
                });
                builder.setNegativeButton(R.string.no_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gps.setChecked(true);
                        gps.setButtonDrawable(R.drawable.ic_location_on_black_24dp);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
            }
        });

    }

    private Weather weatherDetails(String jsonData) throws JSONException {
        Weather mWeather = new Weather();

        mWeather.setCurrentWeather(getCurrent(jsonData));
        mWeather.setHourlyWeather(getHourly(jsonData));
        mWeather.setDailyWeather(getDaily(jsonData));

        return mWeather;
    }

    private DailyWeather[] getDaily(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject forecast = jsonObject.getJSONObject("forecast");
        JSONArray forecastDay = forecast.getJSONArray("forecastday");

        DailyWeather[] dailys = new DailyWeather[forecastDay.length()];

        for (int i = 0; i < dailys.length; i++) {
            JSONObject jsonDay = forecastDay.getJSONObject(i);

            DailyWeather dailyWeather = new DailyWeather();

            dailyWeather.setDate(jsonDay.getLong("date_epoch"));

            JSONObject day = jsonDay.getJSONObject("day");

            dailyWeather.setMinTemperature(day.getDouble("mintemp_c"));
            dailyWeather.setMaxTemperature(day.getDouble("maxtemp_c"));

            JSONObject condition = day.getJSONObject("condition");

            dailyWeather.setDesciption(condition.getString("text"));
            dailyWeather.setIcon(condition.getString("icon"));

            dailys[i] = dailyWeather;
        }
        return dailys;
    }

    private HourlyWeather[] getHourly(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject forecast = jsonObject.getJSONObject("forecast");
        JSONArray forecastDay = forecast.getJSONArray("forecastday");

        HourlyWeather[] hours = new HourlyWeather[48];

        for (int i = 0; i < 2; i++) {
            JSONObject jsonHour = forecastDay.getJSONObject(i);
            JSONArray hour = jsonHour.getJSONArray("hour");

            for (int j = 0; j < hour.length(); j++) {

                JSONObject finalHour = hour.getJSONObject(j);
                HourlyWeather hourWeather = new HourlyWeather();

                hourWeather.setTime(finalHour.getLong("time_epoch"));

                hourWeather.setDayOrNight(finalHour.getInt("is_day"));
                hourWeather.setTemperature(finalHour.getDouble("temp_c"));

                JSONObject condition = finalHour.getJSONObject("condition");
                hourWeather.setDescription(condition.getString("text"));
                hourWeather.setIcon(condition.getString("icon"));

                hours[(i*hour.length())+j]=hourWeather;
            }

        }
        return hours;
    }

    private CurrentWeather getCurrent(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject location = jsonObject.getJSONObject("location");

        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setLocation(location.getString("name"));

        JSONObject current = jsonObject.getJSONObject("current");

        currentWeather.setTemperature(current.getDouble("temp_c"));
        currentWeather.setFeelsTemperarure(current.getDouble("feelslike_c"));
        currentWeather.setWind(current.getDouble("wind_kph"));
        currentWeather.setHumidity(current.getInt("humidity"));
        currentWeather.setPressure(current.getDouble("pressure_mb"));
        currentWeather.setDayOrNight(current.getInt("is_day"));

        JSONObject condition = current.getJSONObject("condition");

        currentWeather.setIcon(condition.getString("icon"));
        currentWeather.setDescription(condition.getString("text"));

        return currentWeather;
    }

    private UpdateCity[] getCityName(String jsonData) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonData);
        UpdateCity[] city = new UpdateCity[jsonArray.length()];

        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject cityName = jsonArray.getJSONObject(i);
            UpdateCity mUpdateCity = new UpdateCity();

            mUpdateCity.setCityName(cityName.getString("name"));
            mUpdateCity.setLatitude(cityName.getDouble("lat"));
            mUpdateCity.setLongitude(cityName.getDouble("lon"));

            city[i] = mUpdateCity;
        }
        return city;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected()) {
            isAvailable=true;
        }
        return  isAvailable;
    }

    private void somethingIsWrong() {
        AlertDialogFragment dialogFragment = new AlertDialogFragment();
        dialogFragment.show(getFragmentManager(), "ERROR");
    }

    private class GetWeather extends AsyncTask<String, Void, Object>{
        ProgressDialog mProgressDialog = new ProgressDialog(MainActivity.this);
        Weather mFinalWeather = new Weather();
        UpdateCity[] mCity;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.setTitle(getString(R.string.waiting_info));
            mProgressDialog.show();
        }

        @Override
        protected Object doInBackground(String... params) {
            String keyApi = "1c2572456e004527ad5160142170809";
            String forecastURL;

            if(params.length == 1) {
                String cityName = params[0];
                forecastURL =
                        "http://api.apixu.com/v1/search.json?key=" + keyApi +"&q=" + cityName;
            }
            else {
                String latitude = params[0];
                String longitude = params[1];

                forecastURL =
                        "http://api.apixu.com/v1/forecast.json?key=" + keyApi + " &q="
                                + latitude + "," + longitude + "&days=8";
            }

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(forecastURL)
                    .build();

            if(isNetworkAvailable()) {

                try {
                    Response response = client.newCall(request).execute();
                    String jsonData= response.body().string();
                    if (response.isSuccessful()) {
                        if(params.length == 1) {
                            mCity = new UpdateCity[getCityName(jsonData).length];
                            mCity = getCityName(jsonData);
                        }
                        else {
                            mFinalWeather = weatherDetails(jsonData);
                        }
                    } else {
                        somethingIsWrong();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if(params.length == 2) return mFinalWeather;
            else return mCity;
        }

        @Override
        protected void onPostExecute(Object object) {
            super.onPostExecute(object);

            if(!isNetworkAvailable()) {
                Toast.makeText(MainActivity.this,
                        getString(R.string.no_internet_conection), Toast.LENGTH_LONG).show();
            }

            if(object instanceof Weather) {
                if(mFinalWeather.getCurrentWeather() == null &&
                        mFinalWeather.getDailyWeather() == null &&
                        mFinalWeather.getHourlyWeather() == null)
                    mFixedTabsPagerAdapter = new FixedTabsPagerAdapter(getSupportFragmentManager());
                else
                    mFixedTabsPagerAdapter = new FixedTabsPagerAdapter(getSupportFragmentManager(),
                            (Weather) object);

                mViewPager = (ViewPager) findViewById(R.id.container);
                mViewPager.setAdapter(mFixedTabsPagerAdapter);
                mViewPager.setPageTransformer(true, new ZoomOutSlideTransformer());
                mViewPager.setCurrentItem(1);

                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(mViewPager);
            }
            else if(object instanceof UpdateCity[]) {
                if(mCity.length > 1) {
                    Intent intent = new Intent(MainActivity.this, SearchCity.class);
                    intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.putExtra(CITY, (UpdateCity[]) object);
                    startActivity(intent);
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(getString(R.string.warning));
                    builder.setMessage(R.string.not_found_city);
                    builder.setPositiveButton(R.string.ok_button,null);

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }

            mProgressDialog.dismiss();

        }
    }
}

