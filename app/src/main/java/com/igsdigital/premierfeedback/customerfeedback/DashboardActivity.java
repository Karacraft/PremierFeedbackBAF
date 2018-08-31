package com.igsdigital.premierfeedback.customerfeedback;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ListPopupWindowCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.Toast;

import com.igsdigital.premierfeedback.R;
import com.igsdigital.premierfeedback.account.Authenticator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by       duke / Kara Craft
 * For Project      Customer Feed Back
 * Dated            Jan 18 2016
 * File Name        DashboardActivity.java
 * Comments
 */
public class DashboardActivity extends Activity
{
    /**
     * Added for SyncService/SyncAdapter
     */
    // Constants
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = Authenticator.KEY_ACCOUNT_TYPE;
    // The account name
    public static final String ACCOUNT = "dummyaccount";
    // Instance fields
    Account mAccount;       //Android Account for Account Manager
    /**
     * ----------------------------------------
     */
    public static final int REQUEST_USERLOGIN = 901;            //Request Code
    public static final int REQUEST_ACCOUNT_LIST = 902;         //Request Code

    ImageView imageViewLogin;
    ImageView imageViewReports;
    ImageView imageViewFeedback;

    boolean loggedIn=false;

    ProgressDialog progressDialog;
    String username;
    Integer responseCodeFromRest;
    SharedPreferences prefs;

    ListPopupWindow popupWindow;
    String[] reports = {"Daily","Weekly","Monthly","Exit"};


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //Setup Controls
        imageViewLogin = (ImageView) findViewById(R.id.imageView_logout);
        imageViewReports = (ImageView) findViewById(R.id.imageView_reports);
        imageViewFeedback = (ImageView) findViewById(R.id.imageView_feedback);

        progressDialog = new ProgressDialog(DashboardActivity.this);
        popupWindow = new ListPopupWindow(this);

        popupWindow.setAdapter(new ArrayAdapter(this,R.layout.activity_list_report_menu,reports));
        popupWindow.setAnchorView(imageViewReports);


        /** For SyncService SyncAdapter */
        // Create the dummy account
        //mAccount = CreateSyncAccount(this);

        /** if there is no account, show a loginActivity and
         * create an Account.
         */
        if (!checkAccount())
        {
            loggedIn=false;
            imageViewLogin.setImageResource(R.drawable.login);
            imageViewFeedback.setImageResource(R.drawable.feedback_disabled);
            imageViewFeedback.setEnabled(false);
            imageViewReports.setImageResource(R.drawable.reports_disabled);
            imageViewReports.setEnabled(false);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, REQUEST_USERLOGIN);
        } else{
            loggedIn=true;
            imageViewLogin.setImageResource(R.drawable.logout);
        }
        /** if there are multiple accounts, then let user choose one
         *  and start the activity
         */
        checkMultipleAccounts();

        //Wire Controls
        imageViewLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (loggedIn){
                    doLogOut();
                }
                if (!loggedIn){
                    doLogin();
                }
            }
        });

        imageViewFeedback.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(DashboardActivity.this, SplashActivity.class);
                startActivity(intent);
                /** we don't want user to go back to dashboard by back button **/
                finish();
            }
        });

        imageViewReports.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                Intent intent = new Intent(DashboardActivity.this,ReportsActivity.class);
//                startActivity(intent);
                popupWindow.show();
            }
        });

        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(DashboardActivity.this,reports[i].toString(), Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
                String option=reports[i].toString();
                if(option.equals("Exit")){
                    return;
                }
                /** Get customerName from preferences **/
                prefs = getSharedPreferences(Constants.PREFS_NAME,MODE_PRIVATE);
                if (prefs.contains(getResources().getString(R.string.usernamekey))){
                    username = prefs.getString(getResources().getString(R.string.usernamekey),"");
                }
                String url = Constants.APP_READ_FEEDBACK + "username=" + username + "&option=" + option;
                Log.d(Constants.TAG, "onClick: " + url);
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
                Intent intent = new Intent(DashboardActivity.this,ReportsActivity.class);
                intent.putExtra("username",username);
                intent.putExtra("option",option);
                startActivity(intent);
            }
        });

    }

    //----------------
    public void doLogOut(){
        prefs = getSharedPreferences(Constants.PREFS_NAME,MODE_PRIVATE);
        username = prefs.getString(getResources().getString(R.string.usernamekey),"");
        String userid = prefs.getString(getResources().getString(R.string.useridkey),"");
        Log.d(Constants.TAG, "doLogOut: " + username + " " + userid) ;
        new UserLogoutTask().execute();
    }
    //-------------
    public void doLogin(){
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        startActivityForResult(intent, REQUEST_USERLOGIN);
    }
    //---------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case REQUEST_USERLOGIN:
                if (resultCode == RESULT_OK && data != null)
                {
                    imageViewLogin.setImageResource(R.drawable.logout);
                    imageViewReports.setEnabled(true);
                    imageViewReports.setImageResource(R.drawable.reports);
                    imageViewFeedback.setEnabled(true);
                    imageViewFeedback.setImageResource(R.drawable.feedback);
                    loggedIn=true;
                    //System.out.println("Success in user login - Setting LoggedIn to True");
                }
                if (resultCode == RESULT_CANCELED){
                    imageViewLogin.setImageResource(R.drawable.login);
                    imageViewFeedback.setImageResource(R.drawable.feedback_disabled);
                    imageViewFeedback.setEnabled(false);
                    imageViewReports.setImageResource(R.drawable.reports_disabled);
                    imageViewReports.setEnabled(false);

                    loggedIn=false;
                    int code = getIntent().getIntExtra("RESPONSECODE",0);
                    if (code == 401){
                        Toast.makeText(DashboardActivity.this, "Response Code : 401 , User Already Logged In", Toast.LENGTH_SHORT).show();
                    }
                    else if (code == 400){
                        Toast.makeText(DashboardActivity.this, "Response Code : 400 , Invalid/Unknown User", Toast.LENGTH_SHORT).show();
                    }
//                    else {
//                        Toast.makeText(DashboardActivity.this, "Response Code : " + code + " Unknown error", Toast.LENGTH_SHORT).show();
//                    }
                    //System.out.println("User Pressed Cancelled - Setting LoggedIn to False");
                    //TODO::add your code to change button status
                    //Toast.makeText(DashboardActivity.this, "Unable to log you in.", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_ACCOUNT_LIST:
                if (resultCode == RESULT_OK && data != null)
                {
                    System.out.println("Success in showing list");
                }
                break;
        }
    }
    //---------------checkMultipleAccounts()---------------------------//
    private void checkMultipleAccounts()
    {

        AccountManager accountManager = AccountManager.get(this);
        Account[] accounts = accountManager.getAccountsByType(Authenticator.KEY_ACCOUNT_TYPE);

        if (accounts.length >= 2)
        {
            Intent intent = AccountManager.newChooseAccountIntent(null, null, new String[]{Authenticator.KEY_ACCOUNT_TYPE}, true, null, null, null, null);
            startActivityForResult(intent, REQUEST_ACCOUNT_LIST);
        }
    }

    //---------------checkAccount()----------------------------//
    /**
     * Checks if an account exist in Android AccountManager
     *
     * @return true or false
     */
    private boolean checkAccount()
    {

        boolean result;

        AccountManager accountManager = AccountManager.get(this);
        Account[] accounts = accountManager.getAccountsByType(Authenticator.KEY_ACCOUNT_TYPE);

        if (accounts.length > 0)
        {
            result = true;
        } else
        {
            result = false;
        }

        return result;
    }
    //-----------------deleteAccount()---------------------------//
    /**
     * Checks if an account exist in Android AccountManager , Delete it.
     *
     */
    private void deleteAccount()
    {

        AccountManager accountManager = AccountManager.get(this);
        Account[] accounts = accountManager.getAccountsByType(Authenticator.KEY_ACCOUNT_TYPE);

        for(Account account : accounts){
            accountManager.removeAccount(account,null,null);
        }

    }
    //----------------------CreateSyncAccount()--------------------------//
    /**
     * Create a new dummy account for the sync adapter
     *
     * @param context The application context
     */
    public static Account CreateSyncAccount(Context context)
    {
        // Create the account type and default account
        Account newAccount = new Account(ACCOUNT, ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager = (AccountManager) context.getSystemService(ACCOUNT_SERVICE);
        accountManager.setUserData(newAccount, "TOKEN", "12345");
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null))
        {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
        } else
        {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }
        return newAccount;
    }
    //-----------------------------resultOfLogoutAttempt()---------------------------
    public void resultOfLogoutAttempt(){
        if(responseCodeFromRest == 200){
            Log.d(Constants.TAG, "resultOfLogoutAttempt: Logged Out");
            deleteAccount();
            prefs = getSharedPreferences(Constants.PREFS_NAME,MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.commit();
           // CustomerDAO customerDAO = new CustomerDAO(this);
            //customerDAO.deleteCustomers();
            //TODO::Delete Feedback Here too
            /////
            loggedIn=false;
            imageViewLogin.setImageResource(R.drawable.login);
            imageViewFeedback.setImageResource(R.drawable.feedback_disabled);
            imageViewFeedback.setEnabled(false);
            imageViewReports.setImageResource(R.drawable.reports_disabled);
            imageViewReports.setEnabled(false);

//            Toast.makeText(DashboardActivity.this, "User Logged out", Toast.LENGTH_SHORT).show();
        } else if(responseCodeFromRest == 400){
            Toast.makeText(DashboardActivity.this, "Invalid User. Error 400", Toast.LENGTH_SHORT).show();
        } else if(responseCodeFromRest == 401){
            Toast.makeText(DashboardActivity.this, "User already logged In. Error 401", Toast.LENGTH_SHORT).show();
        }
//        else {
//            Toast.makeText(DashboardActivity.this, "Unknown Error " + responseCodeFromRest, Toast.LENGTH_SHORT).show();
//        }
    }
    //
    public class UserLogoutTask extends AsyncTask<Void, Void,Void>
    {

        @Override
        protected Void doInBackground(Void... params)
        {
            // TODO: attempt authentication against a network service.
            String query=Constants.APP_LOGOUT + "username=" + username;

            Log.d(Constants.TAG, "doInBackground: " + query);

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
                responseCodeFromRest = flag;

            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
            // TODO: register the new account here.
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            if( progressDialog!= null && progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            //Call Inline function
            resultOfLogoutAttempt();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Logging Out User");
            progressDialog.show();
        }

        @Override
        protected void onCancelled()
        {
            if( progressDialog!= null && progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            //Call inline function
            resultOfLogoutAttempt();
        }
    }

    public class PostJsonArrayToRest extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {

            HttpURLConnection connection=null;

            Calendar c = Calendar.getInstance();
            System.out.println("Current time => " + c.getTime());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = df.format(c.getTime());

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("created_at",date);
                jsonObject.put("happy",1);
                jsonObject.put("indifferent",0);
                jsonObject.put("angry",1);
                jsonObject.put("sa",1);
                jsonObject.put("loa",1);
                jsonObject.put("su",0);
                jsonObject.put("sd",0);
                jsonObject.put("tt",1);
                jsonObject.put("username","alijib");
                jsonObject.put("customername","Naeem Janjua");
                jsonObject.put("customeraccountnumber","12345-698759-5632");
                jsonObject.put("comments","JSON Working or NOt");

            } catch (JSONException e) {
                e.printStackTrace();
            }

//            String query=Constants.APP_POST_FEEDBACK + "json=" + jsonObject.toString();
//            String query=Constants.APP_POST_FEEDBACK + "array:" +feedbackArray.toString();

            String name = "Naeem Janjua";

            String comments = "Nothing is working :(";


            String data = "created_at=" + Helper.replaceSpaces(date)
                    + "&updated_at=" + Helper.replaceSpaces(date)
                    + "&happy=1"
                    + "&indifferent=0"
                    + "&angry=1"
                    + "&sa=1"
                    + "&loa=1"
                    + "&su=1"
                    + "&sd=0"
                    + "&tt=0"
                    + "&username=alijib"
                    + "&cn=" + Helper.replaceSpaces(name)
                    + "&can=" + Helper.replaceSpaces("12343 34343 34343434")
                    + "&com=" + Helper.replaceSpaces(comments);

            String query=Constants.APP_POST_FEEDBACK + data;

            Log.d(Constants.TAG, "doInBackground:Posting Array " + query);

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

                Log.d(Constants.TAG, "doInBackground: Response Code " + connection.getResponseCode());
                Log.d(Constants.TAG, "doInBackground: Data is : " + Helper.readData(connection));

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
            progressDialog.setMessage("Uploading feedback");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(progressDialog.isShowing() && progressDialog != null){
                progressDialog.dismiss();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
