package com.igsdigital.premierfeedback.customerfeedback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.igsdigital.premierfeedback.R;
import com.igsdigital.premierfeedback.data.SqlHelper;

/**
 * Created by       duke / Kara Craft
 * For Project      Customer Feed Back
 * Dated            Jan 18 2016
 * File Name        SelectionActivity.java
 * Comments
 */
public class SelectionActivity extends Activity {

    ImageView happy;
    ImageView inDifferent;
    ImageView angry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        //Setup Buttons
        happy = (ImageView) findViewById(R.id.imageView_happy);
        inDifferent = (ImageView) findViewById(R.id.imageView_inDifferent);
        angry = (ImageView) findViewById(R.id.imageView_angry);

        //Wire the controls
        happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectionActivity.this, ThanksActivity.class);
                intent.putExtra(Constants.INTENT_SENT_BY, Constants.HAPPY);
                intent.putExtra(SqlHelper.COLUMN_HAPPY, Constants.CUSTOMER_HAPPY);
                startActivity(intent);
                finish();
            }
        });

        inDifferent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectionActivity.this, ThanksActivity.class);
                intent.putExtra(Constants.INTENT_SENT_BY, Constants.INDIFFERENT);
                intent.putExtra(SqlHelper.COLUMN_INDIFFERENT, Constants.CUSTOMER_INDIFFERENT);
                startActivity(intent);
                finish();
            }
        });

        angry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectionActivity.this, QuestionsActivity.class);
                intent.putExtra(Constants.INTENT_SENT_BY, Constants.ANGRY);
                intent.putExtra(SqlHelper.COLUMN_ANGRY, Constants.CUSTOMER_ANGRY);
                startActivity(intent);
                //finish();
            }
        });
    }
}
