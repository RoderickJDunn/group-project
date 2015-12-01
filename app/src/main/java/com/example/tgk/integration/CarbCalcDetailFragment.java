package com.example.tgk.integration;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Roderick on 2015-11-29.
 */
public class CarbCalcDetailFragment extends Fragment {
    CarbCalcDbAdapter dbHelper;
    SimpleCursorAdapter dataAdapter;
    long id=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new CarbCalcDbAdapter(getActivity());
        dbHelper.open();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View detailView = inflater.inflate(R.layout.carb_calc_detail, container, false);
        Cursor dataAdapter = dbHelper.fetchTripById(id);
        // check if cursor is empty
        if (dataAdapter != null && dataAdapter.getCount() > 0) {
            TextView tripTitle = (TextView) detailView.findViewById(R.id.trip_title_info);
            tripTitle.setText("Trip " + dataAdapter.getLong(0));
            TextView category = (TextView) detailView.findViewById(R.id.category_info);
            category.setText("Category: " + dataAdapter.getString(1));
            TextView vehicle = (TextView) detailView.findViewById(R.id.vehicletype_info);
            vehicle.setText("Vehicle Type: " + dataAdapter.getString(2));
            TextView distance = (TextView) detailView.findViewById(R.id.distance_info);
            distance.setText(String.valueOf(dataAdapter.getDouble(3)) + " miles");
            TextView co2 = (TextView) detailView.findViewById(R.id.carbon_info);
            co2.setText(String.valueOf(dataAdapter.getDouble(4)) + " metric tons of CO2");
            TextView date = (TextView) detailView.findViewById(R.id.date_info);
            date.setText(dataAdapter.getString(5));
            TextView note = (TextView) detailView.findViewById(R.id.note_info);
            note.setText(dataAdapter.getString(6));
        }
        return detailView;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.id = args.getLong("Trip ID");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
