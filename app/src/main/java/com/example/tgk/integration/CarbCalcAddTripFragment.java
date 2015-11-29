package com.example.tgk.integration;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Roderick on 2015-11-27.
 */
public class CarbCalcAddTripFragment extends Fragment {
    OnTapDoneListener mCallback;
    CarbCalcDbAdapter dbHelper;
    SimpleCursorAdapter dataAdapter;


    public interface OnTapDoneListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onTapDone();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new CarbCalcDbAdapter(getActivity());
        dbHelper.open();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inputTripInfoView = inflater.inflate(R.layout.carb_calc_add_trip, container, false);
        final Button doneButton = (Button)inputTripInfoView.findViewById(R.id.done_button_add_trip);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeInDatabase();
            }
        });

        Spinner vehicleSpinner = (Spinner)inputTripInfoView.findViewById(R.id.vehicle_input);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this.getActivity(), R.array.vehicles_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleSpinner.setAdapter(spinnerAdapter);
        // Inflate the layout for this fragment
        return inputTripInfoView;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnTapDoneListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTapDoneListener");
        }
    }

    public void storeInDatabase() {
        // get text from each of the input fields in the AddTrip fragment view
        String category = ((EditText)getActivity().findViewById(R.id.category_input)).getText().toString();
        String vehicleType = ((Spinner)getActivity().findViewById(R.id.vehicle_input)).getSelectedItem().toString();
        String distanceString = ((EditText)getActivity().findViewById(R.id.distance_input)).getText().toString();

        String date = ((EditText)getActivity().findViewById(R.id.date_input)).getText().toString();
        String note = ((EditText)getActivity().findViewById(R.id.note_input)).getText().toString();

        dbHelper.insertTrip(category, vehicleType, 1, date, note, "");

        mCallback.onTapDone();


    }
}
