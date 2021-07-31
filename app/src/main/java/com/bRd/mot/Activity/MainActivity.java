package com.bRd.mot.Activity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.bRd.mot.Car.CarCategoryFragment;
import com.bRd.mot.Entity.HouseCategory;
import com.bRd.mot.Helper.ActivityDesigner;
import com.bRd.mot.Home.HomeCategoryFragment;
import com.bRd.mot.Home.HomeItemFragment;
import com.bRd.mot.Job.JobFragment;
import com.bRd.mot.R;

public class MainActivity extends AppCompatActivity {

    boolean exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityDesigner.hideActionBar(this);
    }

    @Override
    public void onBackPressed() {
            if (!exit) {
                Toast.makeText(this, "Натиснете още веднъж за изход!", Toast.LENGTH_SHORT).show();
                exit = true;
            } else {
                this.finish();
            }
    }
}
