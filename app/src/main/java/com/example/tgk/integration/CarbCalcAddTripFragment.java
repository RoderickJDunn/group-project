package com.example.tgk.integration;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Roderick on 2015-11-27.
 */
public class CarbCalcAddTripFragment extends Fragment {

    public interface OnTripSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onTripSelected(long id);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.carb_calc_add_trip, container, false);
    }
}
