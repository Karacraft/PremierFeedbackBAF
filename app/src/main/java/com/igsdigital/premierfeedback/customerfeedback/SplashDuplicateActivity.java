package com.igsdigital.premierfeedback.customerfeedback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.igsdigital.premierfeedback.R;
import com.igsdigital.premierfeedback.data.SqlHelper;

/**
 * Created by       duke / Kara Craft
 * For Project      Customer Feed Back
 * Dated            Jan 17 2016
 * File Name        SplashActivity.java
 * Comments         First Screen a User Sees
 *
 */

public class SplashDuplicateActivity extends Activity
{
    String customerName = "";
    String customerAccountNumber = "";
    RelativeLayout layout;

    //----------------------------------onCreate()-----------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        layout = (RelativeLayout) findViewById(R.id.splash);

        customerName = getIntent().getStringExtra(SqlHelper.COLUMN_CUSTOMERNAME);
        customerAccountNumber = getIntent().getStringExtra(SqlHelper.COLUMN_CUSTOMERACCOUNT);

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //return false;
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Intent intent = new Intent(SplashDuplicateActivity.this,SelectionActivity.class);
                        intent.putExtra(SqlHelper.COLUMN_CUSTOMERNAME, customerName);
                        intent.putExtra(SqlHelper.COLUMN_CUSTOMERACCOUNT, customerAccountNumber);
                        startActivity(intent);
                        break;
                }
                return true;
            }


        });
/*        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do Something
                //        Load New Activity Here
                Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        },3000);*/


    }

}
