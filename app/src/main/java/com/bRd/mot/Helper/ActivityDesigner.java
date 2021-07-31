package com.bRd.mot.Helper;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityDesigner {

    public static void hideActionBar(AppCompatActivity activity){

        ActionBar actionBar = activity.getSupportActionBar();

        if (actionBar != null) {
            actionBar.hide();
        }
    }
}
