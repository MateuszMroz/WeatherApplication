package com.mroz.mateusz.weatherapplication.ui;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.mroz.mateusz.weatherapplication.Weather.GetHourWeather;
import com.mroz.mateusz.weatherapplication.R;
import com.mroz.mateusz.weatherapplication.Weather.CurrentWeather;
import com.mroz.mateusz.weatherapplication.Weather.HourlyWeather;

import java.util.ArrayList;
import java.util.List;

public class CurentlyFragment extends Fragment {

    private CurrentWeather mCurrentWeather;
    private Bundle mBundle;
    private ArrayList<HourlyWeather> mHourlyWeathers;
    private GetHourWeather mGetWether;
    private HourlyWeather[] fullHourlyWeather;
    private ArrayList<HourlyWeather> hourlyList;
    private BarChart mBarChart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_curently,container, false);

        if (mBundle != null) {
            mGetWether = new GetHourWeather(fullHourlyWeather, hourlyList, mBundle);
            mHourlyWeathers = mGetWether.getPartHourlyWeather();

            mCurrentWeather = mBundle.getParcelable(MainActivity.CURENTLY_FORECAST);
            setCurrentWeatherDescription(mCurrentWeather, view);
            setSmallHourlyDescription(view, mHourlyWeathers);

            mBarChart = (BarChart) view.findViewById(R.id.bar_chart);

            List<BarEntry> barEntries = new ArrayList<>();
            for (int i = 0; i <mHourlyWeathers.size() ; i++) {
                barEntries.add(new BarEntry(i, mHourlyWeathers.get(i).getTemperature()));
            }

            BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");
            setBarData(barDataSet);

            BarData barData = new BarData(barDataSet);
            barData.setBarWidth(0.15f);

            YAxis leftAxis = mBarChart.getAxisLeft();
            setLeftAxis(leftAxis);

            mBarChart.setData(barData);
            mBarChart.setNoDataText(getString(R.string.no_data));
            mBarChart.setTouchEnabled(false);
            mBarChart.setScaleEnabled(true);
            mBarChart.animateXY(1500, 1500);
            mBarChart.getAxisRight().setEnabled(false);
            mBarChart.getDescription().setEnabled(false);
            mBarChart.getLegend().setEnabled(false);
            mBarChart.getXAxis().setEnabled(false);
        }
        return view;
    }

    private void setSmallHourlyDescription(View view, ArrayList<HourlyWeather> mHourlyWeathers) {
        ImageView[] imageViews = new ImageView[5];

        imageViews[0] = (ImageView)view.findViewById(R.id.icon1);
        imageViews[1] = (ImageView)view.findViewById(R.id.icon2);
        imageViews[2] = (ImageView)view.findViewById(R.id.icon3);
        imageViews[3] = (ImageView)view.findViewById(R.id.icon4);
        imageViews[4] = (ImageView)view.findViewById(R.id.icon5);

        TextView[] textViews = new TextView[5];
        textViews[0] = (TextView)view.findViewById(R.id.hour1);
        textViews[1] = (TextView)view.findViewById(R.id.hour2);
        textViews[2] = (TextView)view.findViewById(R.id.hour3);
        textViews[3] = (TextView)view.findViewById(R.id.hour4);
        textViews[4] = (TextView)view.findViewById(R.id.hour5);

        for (int i = 0; i < 5; i++) {
            //Drawable drawable = getResources().getDrawable(mHourlyWeathers.get(i).getIconId());
            Drawable drawable = ContextCompat.getDrawable(getContext(),
                    mHourlyWeathers.get(i).getIconId());
            imageViews[i].setImageDrawable(drawable);

            textViews[i].setText(mHourlyWeathers.get(i).getHour());
        }
    }

    private void setCurrentWeatherDescription(CurrentWeather mCurrentWeather, View view) {
        TextView mTemperature = (TextView) view.findViewById(R.id.temperature);
        mTemperature.setText(mCurrentWeather.getTemperature() + "");
        TextView mPlaceName = (TextView) view.findViewById(R.id.place_name);
        mPlaceName.setText(mCurrentWeather.getLocation());
        TextView mDescriptionWeather = (TextView) view.findViewById(R.id.description_weather);
        mDescriptionWeather.setText(mCurrentWeather.getDescription());
        TextView mFeelsTemperature = (TextView) view.findViewById(R.id.feels_temperature);
        mFeelsTemperature.setText(mCurrentWeather.getFeelsTemperarure() + "°C");
        TextView mWind = (TextView) view.findViewById(R.id.wind);
        mWind.setText(mCurrentWeather.getWind() + " km/h");
        TextView mHumidity = (TextView) view.findViewById(R.id.humidity);
        mHumidity.setText(mCurrentWeather.getHumidity() + "%");
        TextView mPressure = (TextView) view.findViewById(R.id.pressure);
        mPressure.setText(mCurrentWeather.getPressure() + " hPa");

        Drawable drawable = ContextCompat.getDrawable(getContext(),mCurrentWeather.getIconId());
        ImageView mImageView = (ImageView) view.findViewById(R.id.imageView);
        mImageView.setImageDrawable(drawable);
    }

    private void setBarData(BarDataSet barDataSet) {
        IValueFormatter iValueFormatter = new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return ((int)value) + "°C";
            }
        };

        barDataSet.setColor(Color.rgb(223,169,98));
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextSize(15f);
        barDataSet.setFormSize(15f);
        barDataSet.setValueFormatter(iValueFormatter);
        barDataSet.setDrawValues(true);
        barDataSet.setHighlightEnabled(false);

    }

    private void setLeftAxis(YAxis leftAxis) {
        leftAxis.setSpaceBottom(20);
        leftAxis.setSpaceTop(20);
        leftAxis.setDrawLabels(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setZeroLineColor(Color.BLACK);
        leftAxis.setDrawLimitLinesBehindData(true);
    }

}
