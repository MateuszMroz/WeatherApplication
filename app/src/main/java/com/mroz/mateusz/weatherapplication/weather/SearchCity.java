package com.mroz.mateusz.weatherapplication.weather;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mroz.mateusz.weatherapplication.R;
import com.mroz.mateusz.weatherapplication.ui.MainActivity;

import java.util.Arrays;

public class SearchCity extends ListActivity {
    private UpdateCity[] mTown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Parcelable[] mParceleable = intent.getParcelableArrayExtra(MainActivity.CITY);
        mTown = Arrays.copyOf(mParceleable, mParceleable.length, UpdateCity[].class);

        ListView listCity = getListView();
        ArrayAdapter<UpdateCity> listAdapter = new ArrayAdapter<UpdateCity>(this,
                android.R.layout.simple_list_item_1, mTown) {
            @Override
            public View getView(int position, View v, ViewGroup parent)
            {
                TextView view = (TextView)super.getView(position,v,parent);
                view.setTextColor(Color.WHITE);

                return view;
            }
        };
        listCity.setBackgroundResource(R.drawable.main_background);
        listCity.setAdapter(listAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.LATITUDE_SEARCH_CITY, mTown[position].getLatitude());
        intent.putExtra(MainActivity.LONGITUDE_SEARCH_CITY, mTown[position].getLongitude());
        startActivity(intent);
    }
}
