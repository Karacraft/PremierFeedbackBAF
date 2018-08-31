package com.igsdigital.premierfeedback.customerfeedback;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.igsdigital.premierfeedback.account.Authenticator;
import com.igsdigital.premierfeedback.R;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A login screen that offers login via email/password.
 *
 * Since we are using Account Authenticator, it is necessary that we extend the LoginActivity with
 * AccountAuthenticatorActivity
 * This allow us to send response from activity to Account Manager.
 */
public class LoginActivity extends AccountAuthenticatorActivity
{
    EditText editTextUserId;            //User ID Textbox
    EditText editTextPassword;          //User Password Textbox
    TextView textViewLoginInfo;         //User Login Info Text View
    ImageView imageViewSignin;          // Button TO Sign in
    ImageView imageViewCancel;          //Button to Cancel
    Boolean hasErrors=false;            //Flag to check if there are errors

    String username;        //Username
    String password;        //Password
    Integer responseCodeFromRest=0;    //Rest Response
    String customerFromRest;        //Customer Data
    ProgressDialog progressDialog;  //Progress Dialog for Network Working

    SharedPreferences prefs;        //Shared Preferences to save User data

    //----------------------------------onCreate()-----------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Setup Controls
        setControls();
        //Setup ProgressDialog
        progressDialog = new ProgressDialog(LoginActivity.this);
        //Setup Animation

        editTextUserId.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                textViewLoginInfo.setText("");
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        editTextPassword.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                textViewLoginInfo.setText("");
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

    }
    //----------------------------------setControls()-----------------------------//
    private void setControls(){
        editTextUserId = (EditText) findViewById(R.id.editText_customer_name);
        editTextPassword = (EditText) findViewById(R.id.editText_account_number);
        textViewLoginInfo = (TextView)findViewById(R.id.textView_login_info);
        textViewLoginInfo.setTextColor(Color.RED);
        imageViewSignin = (ImageView) findViewById(R.id.imageView_login_signin);
        imageViewCancel = (ImageView) findViewById(R.id.imageView_login_cancel);
    }
    //----------------------------------isEmailValid()-----------------------------//
    private boolean isEmailValid(String email)
    {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }
    //----------------------------------isPasswordValid()-----------------------------//
    private boolean isPasswordValid(String password)
    {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
    //----------------------------------isUserIdValid()-----------------------------//
    private boolean isUserIdValid(String userid){
        return userid.length() > 6;
    }
    //----------------------------------doLogin()-----------------------------//
    public void doLogin(View view){

        username = editTextUserId.getText().toString();
        password = editTextPassword.getText().toString();
        // Checking hasError flag to false, so that we can check the desired data lenght
        hasErrors=false;

        if(username.length() < 6)
        {
            hasErrors=true;
            //editTextUserId.setBackgroundColor(getResources().getColor(R.color.colorHeading));
            editTextUserId.setError("User Id filed must be 6 characters long");
            //textViewLoginInfo.setText("User Id field must be atleast 6 Characters long.");
        }
        if(password.length() < 4){
            hasErrors=true;
            //editTextPassword.setBackgroundColor(getResources().getColor(R.color.colorHeading));
            editTextPassword.setError("Password filed must be 4 Characters long");
            //textViewLoginInfo.setText("Password field must be 4 Characters long.");
        }
        //If there are errors, then return
        if(hasErrors){
            return;
        }
        // Now that we have done some simple "client side" validation it
        // is time to check with the server
        //Log.d(Constants.TAG, "doLogin: Calling Asynctask.");
        new UserLoginTask().execute();
    }
    //-----------------getMacAddress()---------------------------
    public String getMacAddress(){
        //WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return  wifiInfo.getMacAddress();
    }
    //---------------------doCancel()----------------------------
    public void doCancel(View view){
        //Log.d(Constants.TAG, "doCancel: Called");
        final Intent intent = new Intent();
        this.setResult(RESULT_CANCELED, intent);
        this.finish();
        finish();
    }
    //---------------------resultOfLoginAttempt()-----------------
    public void resultOfLoginAttempt(){
        Log.d(Constants.TAG, "resultOfLoginAttempt: " + responseCodeFromRest);
        if (responseCodeFromRest == 200){
            AccountManager accountManager = AccountManager.get(this);

            // This is the magic that addes the account to the Android Account Manager
            final Account account = new Account(username, Authenticator.KEY_ACCOUNT_TYPE);
            accountManager.addAccountExplicitly(account,password,null);
            // Save Preferences
            prefs = getSharedPreferences(Constants.PREFS_NAME,MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(getResources().getString(R.string.usernamekey),username);
            editor.putString(getResources().getString(R.string.userpasskey),password);
            editor.putBoolean(getResources().getString(R.string.userloggedinkey),true);
            editor.commit();

            //Log.d(Constants.TAG,prefs.getString(getResources().getString(R.string.usernamekey),""));

            Log.d(Constants.TAG, "resultOfLoginAttempt: Username = " + username + " Password = " + password);

            // Now we tell our caller, could be the Android Account Manager or even our own application
            // that the process was successful
            final Intent intent = new Intent();
            intent.putExtra(AccountManager.KEY_ACCOUNT_NAME,username);
            intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE,Authenticator.KEY_ACCOUNT_TYPE);
            intent.putExtra(AccountManager.KEY_AUTHTOKEN,Authenticator.KEY_ACCOUNT_TYPE);
            this.setResult(RESULT_OK, intent);
            this.finish();

        } else {
            final Intent intent = new Intent();
            intent.putExtra("RESPONSECODE",responseCodeFromRest);
            this.setResult(RESULT_CANCELED, intent);
            this.finish();
        }
    }
    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... params)
        {
            // TODO: attempt authentication against a network service.
            String query= Constants.APP_LOGIN + "username=" + username
                    + "&password=" + password;

            Log.d(Constants.TAG, "doInBackground: Query String " + query);
            try {
                URL url= new URL(query);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setConnectTimeout(5000);
                connection.setInstanceFollowRedirects(false);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-type", "application/json");
                connection.setRequestProperty("charset", "utf-8");
                //Write post Data
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeUTF(query);
                out.flush();
                out.close();

                int flag = connection.getResponseCode();
                // 200 = User Logged In
                // 400 = Invalid User
                // 401 = User Already Logged In
                if (flag == 200){
                    customerFromRest = Helper.readData(connection);
                }
                responseCodeFromRest=flag;

            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            if( progressDialog!= null && progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            //Call Inline function
            resultOfLoginAttempt();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Logging In User");
            progressDialog.show();
        }

        @Override
        protected void onCancelled()
        {
            if( progressDialog!= null && progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            //Call inline function
            resultOfLoginAttempt();
        }
    }
}

