package com.example.tgk.integration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity2 extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_two);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        }
        return super.onOptionsItemSelected(item);
    }
    private void go(Class c){
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}
