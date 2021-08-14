package com.bRd.mot.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bRd.mot.Car.CarCategoryActivity;
import com.bRd.mot.Helper.DatabaseHelper;
import com.bRd.mot.Helper.DatabaseSeeder;
import com.bRd.mot.Home.HomeCategoryActivity;
import com.bRd.mot.Job.JobActivity;
import com.bRd.mot.R;

public class MainActivity extends AppCompatActivity {

    private static final int MILLISECONDS_TO_WAIT = 2000;
    private long pressedTime;

    CardView home_cv, car_cv, job_cv, settings_cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper db = new DatabaseHelper(this);
        DatabaseSeeder databaseSeeder = new DatabaseSeeder(db);
        databaseSeeder.seed();

        home_cv = findViewById(R.id.home_cv);
        car_cv = findViewById(R.id.car_cv);
        job_cv = findViewById(R.id.job_cv);
        settings_cv = findViewById(R.id.settings_cv);

        home_cv.setOnClickListener(onClickListener);
        car_cv.setOnClickListener(onClickListener);
        job_cv.setOnClickListener(onClickListener);
        settings_cv.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = view -> {
        if (view.getId() == R.id.home_cv) {
            goToActivity(HomeCategoryActivity.class);
        } else if (view.getId() == R.id.car_cv) {
            goToActivity(CarCategoryActivity.class);
        } else if (view.getId() == R.id.job_cv) {
            goToActivity(JobActivity.class);
        } else if (view.getId() == R.id.settings_cv) {
            goToActivity(SettingsActivity.class);
        }
    };

    private void goToActivity(Class<?> activity) {
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
