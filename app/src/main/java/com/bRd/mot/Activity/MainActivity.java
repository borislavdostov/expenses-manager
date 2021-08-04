package com.bRd.mot.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bRd.mot.Car.CarCategoryActivity;
import com.bRd.mot.Helper.ActivityDesigner;
import com.bRd.mot.Home.HomeCategoryActivity;
import com.bRd.mot.Job.JobActivity;
import com.bRd.mot.R;

public class MainActivity extends AppCompatActivity {

    private static final int MILLISECONDS_TO_WAIT = 2000;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityDesigner.hideActionBar(this);
    }

    public void goToHomeCategoryActivity(View v){
        goToActivity(HomeCategoryActivity.class);
    }

    public void goToCarCategoryActivity(View v){
        goToActivity(CarCategoryActivity.class);
    }

    public void goToJobActivity(View v){
        goToActivity(JobActivity.class);
    }

    public void goToSettingsActivity(View v){
        goToActivity(SettingsActivity.class);
    }

    private void goToActivity(Class<?> activity){
        Intent intent = new Intent(MainActivity.this, activity);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (pressedTime + MILLISECONDS_TO_WAIT > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(this, getString(R.string.press_one_more_time_to_exit), Toast.LENGTH_SHORT).show();
        }

        pressedTime = System.currentTimeMillis();
    }
}
