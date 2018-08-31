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
 * Dated            Jan 31 2016
 * File Name
 * Comments
 */
public class ThanksActivity extends Activity {

    RelativeLayout layout;
    int intentBy = 0;
    Integer sa = 0, loa = 0, su = 0, sd = 0, tt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);

        //Read Intent Data
        String sentBy = getIntent().getStringExtra(Constants.INTENT_SENT_BY);

        if (sentBy.equals(Constants.HAPPY)) {
            intentBy = Constants.CUSTOMER_HAPPY;
        }

        if (sentBy.equals(Constants.INDIFFERENT)) {
            intentBy = Constants.CUSTOMER_INDIFFERENT;
        }

        if (sentBy.equals(Constants.ANGRY)) {
            intentBy = Constants.CUSTOMER_ANGRY;
        }

        layout = (RelativeLayout) findViewById(R.id.layout_thanks_screen);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //return false;
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //Proceed Forward
                        processIntent();
                        break;
                }
                return true;
            }
        });
    }

    private void processIntent() {

        Intent intent = new Intent(ThanksActivity.this, CustomerListViewActivity.class);
        //Get Information
        if (intentBy == Constants.CUSTOMER_HAPPY) {
            intent.putExtra(Constants.INTENT_SENT_BY, Constants.HAPPY);
            intent.putExtra(SqlHelper.COLUMN_STAFFATTITUDE, 0);
            intent.putExtra(SqlHelper.COLUMN_LACKOFAWARENESS, 0);
            intent.putExtra(SqlHelper.COLUMN_STAFFUNAVAILABILITY, 0);
            intent.putExtra(SqlHelper.COLUMN_SYSTEMDOWNTIME, 0);
            intent.putExtra(SqlHelper.COLUMN_TRANSACTIONTIME, 0);
        }

        if (intentBy == Constants.CUSTOMER_INDIFFERENT) {
            intent.putExtra(Constants.INTENT_SENT_BY, Constants.INDIFFERENT);
            intent.putExtra(SqlHelper.COLUMN_STAFFATTITUDE, 0);
            intent.putExtra(SqlHelper.COLUMN_LACKOFAWARENESS, 0);
            intent.putExtra(SqlHelper.COLUMN_STAFFUNAVAILABILITY, 0);
            intent.putExtra(SqlHelper.COLUMN_SYSTEMDOWNTIME, 0);
            intent.putExtra(SqlHelper.COLUMN_TRANSACTIONTIME, 0);
        }

        if (intentBy == Constants.CUSTOMER_ANGRY) {
//            sa = getIntent().getIntExtra(SqlHelper.COLUMN_STAFFATTITUDE, 0);
//            loa = getIntent().getIntExtra(SqlHelper.COLUMN_LACKOFAWARENESS, 0);
//            su = getIntent().getIntExtra(SqlHelper.COLUMN_STAFFUNAVAILABILITY, 0);
//            sd = getIntent().getIntExtra(SqlHelper.COLUMN_SYSTEMDOWNTIME, 0);
//            tt = getIntent().getIntExtra(SqlHelper.COLUMN_TRANSACTIONTIME, 0);

            intent.putExtra(Constants.INTENT_SENT_BY, Constants.ANGRY);
            intent.putExtra(SqlHelper.COLUMN_STAFFATTITUDE, getIntent().getIntExtra(SqlHelper.COLUMN_STAFFATTITUDE, 0));
            intent.putExtra(SqlHelper.COLUMN_LACKOFAWARENESS, getIntent().getIntExtra(SqlHelper.COLUMN_LACKOFAWARENESS, 0));
            intent.putExtra(SqlHelper.COLUMN_STAFFUNAVAILABILITY, getIntent().getIntExtra(SqlHelper.COLUMN_STAFFUNAVAILABILITY, 0));
            intent.putExtra(SqlHelper.COLUMN_SYSTEMDOWNTIME, getIntent().getIntExtra(SqlHelper.COLUMN_SYSTEMDOWNTIME, 0));
            intent.putExtra(SqlHelper.COLUMN_TRANSACTIONTIME, getIntent().getIntExtra(SqlHelper.COLUMN_TRANSACTIONTIME, 0));
        }

        startActivity(intent);
        finish();
    }


}
