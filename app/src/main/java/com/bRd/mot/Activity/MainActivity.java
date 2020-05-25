package com.bRd.mot.Activity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bRd.mot.Car.CarCategoryFragment;
import com.bRd.mot.Entity.HomeCategory;
import com.bRd.mot.Home.HomeCategoryFragment;
import com.bRd.mot.Home.HomeItemFragment;
import com.bRd.mot.Job.JobFragment;
import com.bRd.mot.Menu.MenuFragment;
import com.bRd.mot.R;

public class MainActivity extends AppCompatActivity implements
        MenuFragment.OnFragmentInteractionListener,
        HomeCategoryFragment.OnFragmentInteractionListener,
        HomeItemFragment.OnFragmentInteractionListener,
        CarCategoryFragment.OnFragmentInteractionListener,
        JobFragment.OnFragmentInteractionListener {

    FragmentManager fragmentManager;
    int exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        exit = 0;

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        goToMenuFragment();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            exit = 0;
            getSupportFragmentManager().popBackStack();
        } else {
            if (exit == 0) {
                Toast.makeText(this, "Натиснете още веднъж за изход!", Toast.LENGTH_SHORT).show();
                exit++;
            } else
                this.finish();
        }
    }

    @Override
    public void popBackStack() {
        fragmentManager.popBackStack();
        exit = 0;
    }

    public void goToMenuFragment() {

        MenuFragment menuFragment = new MenuFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, menuFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void enterToHomeCategoryFragment() {

        HomeCategoryFragment homeCategoryFragment = new HomeCategoryFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.content, homeCategoryFragment);
        fragmentTransaction.addToBackStack(homeCategoryFragment.toString());
        fragmentTransaction.commit();
    }

    @Override
    public void enterToHomeItemFragment(HomeCategory homeCategory) {

        HomeItemFragment homeItemFragment = new HomeItemFragment();
        homeItemFragment.homeCategory = homeCategory;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.content, homeItemFragment);
        fragmentTransaction.addToBackStack(homeItemFragment.toString());
        fragmentTransaction.commit();
    }

    @Override
    public void enterToCarCategoryFragment() {

        CarCategoryFragment carCategoryFragment = new CarCategoryFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.content, carCategoryFragment);
        fragmentTransaction.addToBackStack(carCategoryFragment.toString());
        fragmentTransaction.commit();
    }

    @Override
    public void enterToJobFragment() {

        JobFragment jobFragment = new JobFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.content, jobFragment);
        fragmentTransaction.addToBackStack(jobFragment.toString());
        fragmentTransaction.commit();
    }

    @Override
    public void enterToSettingsFragment() {

        // TODO: 8.11.2019 г.  
//        SettingsFragment settingsFragment = new SettingsFragment();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);
//        fragmentTransaction.replace(R.id.content, settingsFragment);
//        fragmentTransaction.addToBackStack(settingsFragment.toString());
//        fragmentTransaction.commit();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
