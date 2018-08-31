package com.igsdigital.premierfeedback.customerfeedback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.igsdigital.premierfeedback.R;
/**
 * Created by       duke / Kara Craft
 * For Project      Customer Feed Back
 * Dated            Jan 17 2016
 * File Name        SplashActivity.java
 * Comments         Goes to CustomerListViewActivity
 *
 */

public class SplashActivity extends Activity
{
    RelativeLayout layout;

    //----------------------------------onCreate()-----------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        layout = (RelativeLayout) findViewById(R.id.splash);

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //return false;
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        //Intent intent = new Intent(SplashActivity.this,CustomerListViewActivity.class);
                        Intent intent = new Intent(SplashActivity.this,SelectionActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }

        });
    }

}
