package com.example.tgk.integration;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Roderick on 2015-11-29.
 */
public class CarbCalcDetailFragment extends Fragment {
    CarbCalcDbAdapter dbHelper;
    SimpleCursorAdapter dataAdapter;
    OnTapDeleteListener mCallback;
    long id=0;

    public interface OnTapDeleteListener {
        public void onTapDeleteButton(long id);
    }

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

        Button deleteButton = (Button)detailView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CarbCalcDetailFragment.this.getActivity());
                builder.setTitle("Delete this trip?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.removeTrip(id);
                        mCallback.onTapDeleteButton(id);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

               // AlertDialog confirmDeleteDialog = builder.create();

            }
        });
        return detailView;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.id = args.getLong("Trip ID");
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnTapDeleteListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTapDoneListener");
        }
    }
}
