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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carb_calc_list);

        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.carb_calc_list_frag) != null) {

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

            transaction.add(R.id.carb_calc_list_frag, listFragment).commit();
            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments

            Log.d("Check", "past transaction");
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


        Fragment addTripFrag = new CarbCalcAddTripFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.carb_calc_list_frag, addTripFrag);
        transaction.addToBackStack(null);


        transaction.commit();

    }

    @Override
    public void onTripSelected(long id) {
        CarbCalcDetailFragment detailFragment = new CarbCalcDetailFragment();
    }

    @Override
    public void onTapDone() {
        // Create an instance of ExampleFragment
        CarbCalcListFragment listFragment = new CarbCalcListFragment();

        //  listFragment.setArguments(getIntent().getExtras());
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.carb_calc_list_frag, listFragment).commit();
    }
}
