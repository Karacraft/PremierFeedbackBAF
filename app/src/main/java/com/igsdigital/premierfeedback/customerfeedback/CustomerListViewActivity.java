package com.igsdigital.premierfeedback.customerfeedback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.igsdigital.premierfeedback.R;
import com.igsdigital.premierfeedback.data.Feedback;
import com.igsdigital.premierfeedback.data.FeedbackDAO;
import com.igsdigital.premierfeedback.data.SqlHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Ali Jibran on 23/07/2016.
 */
public class CustomerListViewActivity extends Activity {

    EditText customerName;
    EditText accountNumber;
    ImageView buttonNext;
    TextView txtCustomerInfo;

    Boolean hasError=false;

    //For Upload
    Integer happy= 0 ,inDifferent =0,angry =0;
    Integer sa =0,loa =0,su =0,sd =0,tt = 0;
    String appUserName;
    String customerNameText;
    String accountNumberText;
    String date;        //For Database date
    SharedPreferences prefs;

    //Json Array to be posted to REST
    JSONArray feedbackArray = new JSONArray();
    JSONObject jsonObject = new JSONObject();
    int responseCode=0;
    String responseFromRest;

    //
    ProgressDialog progressDialog;
    // For Upload

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerview);

        //Process Intents
        String sentBy = getIntent().getStringExtra(Constants.INTENT_SENT_BY);
        if(sentBy.equals(Constants.HAPPY)){
            //Sent by Happy
            happy=1;
            inDifferent=0;
            angry=0;
        }
        if(sentBy.equals(Constants.INDIFFERENT)){
            //Sent by Indifferent
            happy = 0;
            inDifferent=1;
            angry=0;
        }
        if(sentBy.equals(Constants.ANGRY)){
            //Sent by Angry
            happy=0;
            inDifferent=0;
            angry=1;
            sa = getIntent().getIntExtra(SqlHelper.COLUMN_STAFFATTITUDE, 0);
            loa = getIntent().getIntExtra(SqlHelper.COLUMN_LACKOFAWARENESS, 0);
            su = getIntent().getIntExtra(SqlHelper.COLUMN_STAFFUNAVAILABILITY, 0);
            sd = getIntent().getIntExtra(SqlHelper.COLUMN_SYSTEMDOWNTIME, 0);
            tt = getIntent().getIntExtra(SqlHelper.COLUMN_TRANSACTIONTIME, 0);
        }

        setControls();

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doNext();
            }
        });
    }
    //---------------setControls()------------------------
    private void setControls(){
        customerName = (EditText) findViewById(R.id.editText_customer_name);
        accountNumber = (EditText) findViewById(R.id.editText_account_number);
        buttonNext = (ImageView) findViewById(R.id.imageView_account_proceed);
        txtCustomerInfo = (TextView)findViewById(R.id.textView_customer_info);
    }
    //---------------doNext()-----------------------------
    public void doNext(){
        String customer = customerName.getText().toString();
        String account = accountNumber.getText().toString();
        //Get Username from Preferences
        prefs = getSharedPreferences(Constants.PREFS_NAME,MODE_PRIVATE);
        appUserName = prefs.getString(getResources().getString(R.string.usernamekey),"");

        //comment = comments.getText().toString();
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = df.format(c.getTime());
        //Set All errors to false
        hasError=false;

        if (customer.length() < 3){
            hasError = true;
            customerName.setBackgroundColor(getResources().getColor(R.color.colorHeading));
            txtCustomerInfo.setText("Customer Name must be at least 3 Characters long.");
        }

        if(account.length() < 10){
            hasError = true;
            accountNumber.setBackgroundColor(getResources().getColor(R.color.colorHeading));
            txtCustomerInfo.setText("Account Number must be at least 10 Characters long.");
        }
        //We met some errors so return
        if(hasError){
            return;
        }

        customerNameText = customer;
        accountNumberText = account;
        Log.d(Constants.TAG, "Customer Name " + customer);
        Log.d(Constants.TAG, "Account Number " + account);
        /** If we have data, send this across to SplashDuplicateActivity for processing **/

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading feedback");
        progressDialog.show();

        new PostJsonArrayToRest().execute();
        //Log.d(Constants.TAG, "doNext: WOrking");
    }

    public void RestResponse(){

        if(progressDialog.isShowing() && progressDialog != null){
            progressDialog.dismiss();
        }

        if(responseCode == 200){
            //Read the rest data
            Log.d(Constants.TAG, "RestResponse:Code " + responseCode + " Response : " + responseFromRest);
            Intent intent = new Intent(CustomerListViewActivity.this,DashboardActivity.class);
            startActivity(intent);
            finish();

        }else {
            Log.d(Constants.TAG, "RestResponse:Code " + responseCode + " Response : " );
            Feedback feedback = new Feedback();
            feedback.setAngry(angry);
            feedback.setCustomerAccountNumber(accountNumber.getText().toString());
            feedback.setCustomerName(customerName.getText().toString());
            feedback.setHappy(happy);
            feedback.setIndifferent(inDifferent);
            feedback.setLackofawareness(loa);
            feedback.setStaffattitude(sa);
            feedback.setStaffunavailability(su);
            feedback.setSystemdowntime(sd);
            feedback.setTransactiontime(tt);
            feedback.setCreated_at(date);
            feedback.setUsername(appUserName);

            FeedbackDAO feedbackDAO = new FeedbackDAO(getApplicationContext());
            feedbackDAO.addFeedback(feedback);
            feedbackDAO.close();

            Intent intent = new Intent(CustomerListViewActivity.this,DashboardActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void startNewActivity(){
        Intent intent = new Intent(CustomerListViewActivity.this,DashboardActivity.class);
        finishAffinity();
        startActivity(intent);
        finish();
        try {
            Thread.sleep(5000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class PostJsonArrayToRest extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {

            HttpURLConnection connection=null;

            String data = "created_at=" + Helper.replaceSpaces(date)
                    + "&updated_at=" + Helper.replaceSpaces(date)
                    + "&happy=" + happy
                    + "&indifferent=" + inDifferent
                    + "&angry=" + angry
                    + "&sa=" + sa
                    + "&loa=" + loa
                    + "&su=" + su
                    + "&sd=" + sd
                    + "&tt=" + tt
                    + "&cn=" + Helper.replaceSpaces(customerNameText)
                    + "&can=" + Helper.replaceSpaces(accountNumberText)
                    + "&username=" + appUserName;

            String query=Constants.APP_POST_FEEDBACK + data;

            Log.d(Constants.TAG, "doInBackground:Posting to REST: " + query);

            try {
                URL url= new URL(query);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setConnectTimeout(5000);
                connection.setInstanceFollowRedirects(false);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-type", "application/json");
                connection.setRequestProperty("charset", "utf-8");
                connection.setRequestProperty("Accept", "application/json");
                //Write post Data
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeUTF(query);
                Log.d(Constants.TAG, "doInBackground:Posting Array " + out.toString());
                out.flush();
                out.close();

                responseCode = connection.getResponseCode();
                responseFromRest = Helper.readData(connection);

            } catch (java.io.IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(getApplicationContext());
//            progressDialog.setMessage("Uploading feedback");
//            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

//            if(progressDialog.isShowing() && progressDialog != null){
//                progressDialog.dismiss();
//            }
//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
            RestResponse();
//                }
//            },1000);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


}

