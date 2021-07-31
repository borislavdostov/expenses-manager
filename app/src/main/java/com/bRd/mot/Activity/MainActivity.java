package com.bRd.mot.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.bRd.mot.Helper.ActivityDesigner;
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

    @Override
    public void onBackPressed() {
        if (pressedTime + MILLISECONDS_TO_WAIT > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Натиснете още веднъж за изход", Toast.LENGTH_SHORT).show();
        }

        pressedTime = System.currentTimeMillis();
    }
}
