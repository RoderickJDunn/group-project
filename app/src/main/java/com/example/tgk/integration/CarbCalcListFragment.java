package com.example.tgk.integration;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Roderick on 2015-11-27.
 */
public class CarbCalcListFragment extends ListFragment {

    CarbCalcDbAdapter dbHelper;
    SimpleCursorAdapter dataAdapter;
    private OnTripSelectedListener mCallback;

    public interface OnTripSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onTripSelected(long id);
        public void notifyListChanged();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        dbHelper = new CarbCalcDbAdapter(getActivity());
        dbHelper.open();



         displayListView();
        // connect to DB in background thread
    }

    private void displayListView() {

        Cursor cursor = dbHelper.fetchAllTrips();
        // The desired columns to be bound
        String[] columns = new String[]{
                CarbCalcDbAdapter.KEY_TRIP_ID,
                CarbCalcDbAdapter.KEY_CATEGORY
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.trip_id,
                R.id.category
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                getActivity(), R.layout.simple_text_view1,
                cursor,
                columns,
                to,
                0);

        setListAdapter(dataAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnTripSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTripSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mCallback.onTripSelected(id);
        getListView().setItemChecked(position, true);
    }
}
