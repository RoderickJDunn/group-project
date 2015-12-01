package com.example.tgk.integration;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class CarbCalculatorActivity extends ActionBarActivity
        implements CarbCalcListFragment.OnTripSelectedListener, CarbCalcAddTripFragment.OnTapDoneListener{

    public static final String TITLE = "CO2 Calculator";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carb_calc_activity);
        setTitle(TITLE);
        // Check whether the activity is using the layout version with
        // the fragment_container for small screens. If so, we must add the first fragment
        if (findViewById(R.id.carb_calc_activity) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of ExampleFragment
            CarbCalcListFragment listFragment = new CarbCalcListFragment();

          //  listFragment.setArguments(getIntent().getExtras());
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.add(R.id.carb_calc_list_view, listFragment).commit();
            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments

            Log.d("Check", "past transaction");
        }
        else {

            CarbCalcDetailFragment detailFragment = new CarbCalcDetailFragment();

            //  listFragment.setArguments(getIntent().getExtras());
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.add(R.id.details_add_pane, detailFragment).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(1, 5, 5, "Add Trip");
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        switch (id){
            case R.id.tip_calc:
                go(TipCalculatorActivity.class);
                break;
            case R.id.time_track:
                go(MainActivity2.class);
                break;
            case R.id.carb_calc:
                go(CarbCalculatorActivity.class);
                break;
            case R.id.contacts:
                go(MainActivity4.class);
                break;
            case 5:
                openAddTrip();
        }
        return super.onOptionsItemSelected(item);
    }
    private void go(Class c){
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    private void openAddTrip() {
        Log.d("Check", "Adding trip");
        // fragment transaction (input trip info)
        // Create new fragment and transaction

        Fragment detailFrag = getFragmentManager().findFragmentById(R.id.details_add_pane);
        if (detailFrag != null) {
            Fragment addTripFrag = new CarbCalcAddTripFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.details_add_pane, addTripFrag);
            transaction.addToBackStack(null);


            transaction.commit();
        }
        else {
            Fragment addTripFrag = new CarbCalcAddTripFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.carb_calc_list_view, addTripFrag);
            transaction.addToBackStack(null);


            transaction.commit();
        }

    }

    @Override
    public void onTripSelected(long id) {
        Bundle args = new Bundle();
        args.putLong("Trip ID", id);
        CarbCalcDetailFragment detailFragment = new CarbCalcDetailFragment();
        detailFragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // get a reference to the details pane
        Fragment currentDetailFrag = getFragmentManager().findFragmentById(R.id.details_add_pane);
        // if the details pane is null, we're in a single-pane layout
        if (currentDetailFrag == null) {
            transaction.replace(R.id.carb_calc_list_view, detailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else {
            // in dual-pane layout
            transaction.replace(R.id.details_add_pane, detailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void onTapDone() {
        // Create an instance of ExampleFragment
        Fragment listFragment = getFragmentManager().findFragmentById(R.id.list_pane);

        if (listFragment != null) {
            //in two-pane view
            CarbCalcDetailFragment detailFragment = new CarbCalcDetailFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.details_add_pane, detailFragment).commit();

            Intent intent = getIntent();
            startActivity(intent);
        }
        else {
            // in single-pane view
            // listFragment.setArguments(getIntent().getExtras());
            CarbCalcListFragment newListFragment = new CarbCalcListFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.carb_calc_list_view, newListFragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }


}
