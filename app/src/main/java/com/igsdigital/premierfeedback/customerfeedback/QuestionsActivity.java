package com.igsdigital.premierfeedback.customerfeedback;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.igsdigital.premierfeedback.custom.PremierCheckBox;
import com.igsdigital.premierfeedback.data.SqlHelper;
import com.igsdigital.premierfeedback.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by       duke / Kara Craft
 * For Project      Customer Feed Back
 * Dated            Jan 31 2016
 * File Name
 * Comments
 */
public class QuestionsActivity extends Activity
{

    PremierCheckBox ch1,ch2,ch3,ch4,ch5;
    ImageView imageViewProceed;

    Integer sa,loa,su,sd,tt;

    ArrayList<PremierCheckBox> checkBoxes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        //Setup Controls
        setControls();
        addCheckBoxesToList();

        imageViewProceed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Check if any one of the chk box is checked or not
                int checked = 0;
                for (PremierCheckBox p : checkBoxes){
                    if (p.isChecked()){
                        checked++;
                    }
                }
                //If its above 0, we have atleast 1 check box
                if (checked>0){
                    processCheckedItems();
                }else {
                    Toast.makeText(QuestionsActivity.this, "Must select a Reason to proceed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void processData(){
        Intent intent = new Intent(QuestionsActivity.this,ThanksActivity.class);
        intent.putExtra(Constants.INTENT_SENT_BY,Constants.ANGRY);
        intent.putExtra(SqlHelper.COLUMN_STAFFATTITUDE,sa);
        intent.putExtra(SqlHelper.COLUMN_LACKOFAWARENESS,loa);
        intent.putExtra(SqlHelper.COLUMN_STAFFUNAVAILABILITY,su);
        intent.putExtra(SqlHelper.COLUMN_SYSTEMDOWNTIME,sd);
        intent.putExtra(SqlHelper.COLUMN_TRANSACTIONTIME,tt);
        startActivity(intent);
        //TODO:: Send data of choice boxes with Intent
        finish();
    }

    private void processCheckedItems() {
        if(ch1.isChecked()){
            sa=1;
        }else { sa = 0; }
        if(ch2.isChecked()){
            loa=1;
        }else { loa = 0;}
        if (ch3.isChecked()){
            su=1;
        }else { su = 0; }

        if (ch4.isChecked()){
            sd=1;
        }else { sd = 0;}

        if (ch5.isChecked()){
            tt=1;
        }else { tt = 0; }
        //Move Ahead
        processData();
    }

    private void addCheckBoxesToList() {

        checkBoxes = new ArrayList<PremierCheckBox>();
        checkBoxes.add(ch1);
        checkBoxes.add(ch2);
        checkBoxes.add(ch3);
        checkBoxes.add(ch4);
        checkBoxes.add(ch5);
    }

    //--------------setControls()----------------//
    private void setControls(){
        ch1 = (PremierCheckBox) findViewById(R.id.chk_staff_attitude);
        ch2 = (PremierCheckBox) findViewById(R.id.chk_lack_of_awareness);
        ch3 = (PremierCheckBox) findViewById(R.id.chk_staff_unavailabiliyt);
        ch4 = (PremierCheckBox) findViewById(R.id.chk_system_down);
        ch5 = (PremierCheckBox) findViewById(R.id.chk_transaction_time);
        imageViewProceed = (ImageView) findViewById(R.id.imageView_questions_proceed);
    }
}
