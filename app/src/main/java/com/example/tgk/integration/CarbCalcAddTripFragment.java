package com.example.tgk.integration;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Roderick on 2015-11-27.
 */
public class CarbCalcAddTripFragment extends Fragment {
    OnTapDoneListener mCallback;
    CarbCalcDbAdapter dbHelper;
    SimpleCursorAdapter dataAdapter;
    private final static double CAR_CARBON_FACTOR = 0.00025;
    private final static double PLANE_CARBON_FACTOR = 0.00007436;
    private final static double MOTORBIKE_CARBON_FACTOR = 0.000195;
    private final static double BUS_CARBON_FACTOR = 0.000175;
    private final static double TRAIN_CARBON_FACTOR = 0.00002;

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
        registerListeners(inputTripInfoView);
        Spinner vehicleSpinner = (Spinner)inputTripInfoView.findViewById(R.id.vehicle_input);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this.getActivity(), R.array.vehicles_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleSpinner.setAdapter(spinnerAdapter);
        // Inflate the layout for this fragment
        return inputTripInfoView;
    }

    private void registerListeners(final View inputTripInfoView) {

        final Button doneButton = (Button)inputTripInfoView.findViewById(R.id.done_button_add_trip);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeInDatabase();
            }
        });

        final EditText distanceInput = (EditText)inputTripInfoView.findViewById(R.id.distance_input);
        final TextView carbonTotalView = (TextView)inputTripInfoView.findViewById(R.id.carbon_field);

        distanceInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String vehicleType = ((Spinner)inputTripInfoView.findViewById(R.id.vehicle_input))
                        .getSelectedItem().toString();
                String distanceString = s.toString();
                if (distanceString != null && !distanceString.trim().equals("")) {
                    double distance = Double.parseDouble(s.toString());
                    switch (vehicleType) {
                        case "Car":
                            carbonTotalView.setText(String.valueOf(distance*CAR_CARBON_FACTOR));
                            break;
                        case "Plane":
                            carbonTotalView.setText(String.valueOf(distance*PLANE_CARBON_FACTOR));
                            break;
                        case "Motorbike":
                            carbonTotalView.setText(String.valueOf(distance*MOTORBIKE_CARBON_FACTOR));
                            break;
                        case "Bus":
                            carbonTotalView.setText(String.valueOf(distance*BUS_CARBON_FACTOR));
                            break;
                        case "Train":
                            carbonTotalView.setText(String.valueOf(distance*TRAIN_CARBON_FACTOR));
                            break;
                    }
                }
                else {
                    carbonTotalView.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
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

        final String category = ((EditText)getActivity().findViewById(R.id.category_input)).getText().toString();
        final String vehicleType = ((Spinner)getActivity().findViewById(R.id.vehicle_input)).getSelectedItem().toString();
        final String distanceString = ((EditText)getActivity().findViewById(R.id.distance_input)).getText().toString();
        final String cO2String = ((TextView)getActivity().findViewById(R.id.carbon_field)).getText().toString();


        final String date = ((EditText)getActivity().findViewById(R.id.date_input)).getText().toString();
        final String note = ((EditText)getActivity().findViewById(R.id.note_input)).getText().toString();

        new AsyncTask<Void,Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                double distance=0;
                double co2Amount=0;
                if (distanceString != null && !distanceString.trim().equals("")) {
                    distance = Double.parseDouble(distanceString);
                    co2Amount = Double.parseDouble(cO2String);
                }
                dbHelper.insertTrip(category, vehicleType, distance, co2Amount, date, note, "");
                return null;
            }
        }.execute();

        mCallback.onTapDone();


    }
}
