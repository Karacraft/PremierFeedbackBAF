package com.igsdigital.premierfeedback.customerfeedback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.igsdigital.premierfeedback.data.Feedback;
import com.igsdigital.premierfeedback.data.FeedbackDAO;
import com.igsdigital.premierfeedback.data.SqlHelper;
import com.igsdigital.premierfeedback.R;

import org.json.JSONArray;
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
 * Dated            Jan 30 2016
 * File Name
 * Comments
 */
public class ReportsActivity extends Activity
{

    WebView browser;
    FeedbackDAO feedbackDAO=null;
    Cursor cursor = null;
    ProgressDialog progressDialog;

    String username;
    String option;
    String feedbackFromRest="";
    int responseCodeFromRest=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        username = getIntent().getStringExtra("username");
        option = getIntent().getStringExtra("option");

        browser = (WebView) findViewById(R.id.webView);

        String url = Constants.APP_READ_FEEDBACK + "username=" + username + "&option=" + option;

        progressDialog = new ProgressDialog(ReportsActivity.this);

        new fetchDataForWeb().execute();
//        browser.loadUrl(url);

    }

    public void responseFromRest(){

        String data="";

        if(responseCodeFromRest == 200){
//            Log.d(Constants.TAG, "responseFromRest : 200 ");
//            Log.d(Constants.TAG, "Feedback " + feedbackFromRest);
            feedbackDAO = new FeedbackDAO(this);
            feedbackDAO.deleteFeedback();

            try {
                JSONObject obj = new JSONObject(feedbackFromRest);
                if (obj.has("feedbacks")){
                    JSONArray jsonArray = obj.getJSONArray("feedbacks");
                    for (int i=0 ; i < jsonArray.length(); i++) {
                        Feedback feedback = new Feedback();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        feedback.setCreated_at(jsonObject.getString(SqlHelper.COLUMN_CREATED_AT));
                        feedback.setHappy(Integer.parseInt(jsonObject.getString(SqlHelper.COLUMN_HAPPY)));
                        feedback.setIndifferent(Integer.parseInt(jsonObject.getString(SqlHelper.COLUMN_INDIFFERENT)));
                        feedback.setAngry(Integer.parseInt(jsonObject.getString(SqlHelper.COLUMN_ANGRY)));
                        feedback.setStaffattitude(Integer.parseInt(jsonObject.getString(SqlHelper.COLUMN_STAFFATTITUDE)));
                        feedback.setLackofawareness(Integer.parseInt(jsonObject.getString(SqlHelper.COLUMN_LACKOFAWARENESS)));
                        feedback.setStaffunavailability(Integer.parseInt(jsonObject.getString(SqlHelper.COLUMN_STAFFUNAVAILABILITY)));
                        feedback.setSystemdowntime(Integer.parseInt(jsonObject.getString(SqlHelper.COLUMN_SYSTEMDOWNTIME)));
                        feedback.setTransactiontime(Integer.parseInt(jsonObject.getString(SqlHelper.COLUMN_TRANSACTIONTIME)));
//                        feedback.setComments(jsonObject.getString(SqlHelper.COLUMN_COMMENTS));
                        feedback.setCustomerName(jsonObject.getString(SqlHelper.COLUMN_CUSTOMERNAME));
                        feedback.setCustomerAccountNumber(jsonObject.getString(SqlHelper.COLUMN_CUSTOMERACCOUNT));
                        feedback.setUsername(jsonObject.getString(SqlHelper.COLUMN_USERNAME));
                        feedbackDAO.addFeedback(feedback);
                    }
                }

                cursor = feedbackDAO.getFeedbacks();

                if(cursor != null){
                    cursor.moveToFirst();
                }
                int columnCount = cursor.getColumnCount();
                int rowCount = cursor.getCount();
                data ="<html><head>\n" +
                        "<style>" +
                        "caption {background-color:#F8D888;}" +
                        "table {width:100%;}" +
                        "table, th, td {border: 1px solid black;border-collapse: collapse;}" +
                        "th {padding: 5px;text-align: center;}" +
                        "td {padding: 5px;text-align: center;}" +
                        "thead {background-color: #f0f0f0;}" +
                        "table#t01 tr:nth-child(even) {background-color: #eee;}" +
                        "table#t01 tr:nth-child(odd) {background-color:#fff;}" +
                        "table#t01 th {background-color: black;color: white;}" +
                        "</style>" +
                        "</head><body><table id=\"t01\">" +
                        "<caption>Daily Feedbacks</caption>" +
                        "<thead>" +
                        "<tr> "+
                        "<td>Date</td>" +
                        "<td>Happy</td>" +
                        "<td>Indifferent</td>" +
                        "<td>Angry</td>" +
                        "<td>Staff Attitude</td>" +
                        "<td>Lack of Awareness</td>" +
                        "<td>Staff Unavailability</td>" +
                        "<td>System Downtime</td>" + "" +
                        "<td>Transaction Time</td>" +
                        "<td>Customer </td>" +
                        "<td>Account Number</td>" +
                        "</tr>" +
                        "</thead>" +
                        "<tbody>";

                String num;
                for (int i = 0; i < rowCount ; i++){
                    data += "<tr>";
                    for (int j = 1 ; j < columnCount -1; j++){
                        num = cursor.getString(j);
                        if (num.equals("0")){
                            data += "<td>-</td>";
                        }else if (num.equals("1")){
                            data += "<td>Yes</td>";
                        }else {
                            data += "<td>" + cursor.getString(j) + "</td>";
//                        Log.d(Constants.TAG, "responseFromRest: " + cursor.getColumnName(j));
                        }
                    }
                    data += "</tr>";
                    cursor.moveToNext();
                }

                data += " </tbody></table></body></html>";
                browser.loadData(data, "text/html", "UTF-8");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if (responseCodeFromRest == 400){
            Toast.makeText(ReportsActivity.this, "No Record Found!" , Toast.LENGTH_SHORT).show();
            data = "<html><body><h3>No Record Found!</h3></body></html>";
            browser.loadData(data,"text/html","UTF-8");
            Log.d(Constants.TAG, "No Record Found " + responseCodeFromRest);
        }else {
            Toast.makeText(ReportsActivity.this, "Error occured at server side : " + responseCodeFromRest, Toast.LENGTH_SHORT).show();
            data = "<html><body><h3>Unable to fetch Records, Please try later.</h3></br><h4>Server Returned : " +
                    responseCodeFromRest + "</h4></body></html>";
            browser.loadData(data,"text/html","UTF-8");
            Log.d(Constants.TAG, "Response Crashed " + responseCodeFromRest);
        }
    }

    public class fetchDataForWeb extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... params)
        {

            Calendar c = Calendar.getInstance();
            System.out.println("Current time => " + c.getTime());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String date = df.format(c.getTime());

            String query= Constants.APP_READ_FEEDBACK + "username=" + username + "&date=" + date + "&option=" + option;

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
                feedbackFromRest = Helper.readData(connection);
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
            responseFromRest();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Fetching Feed-backs...");
            progressDialog.show();
        }

        @Override
        protected void onCancelled()
        {
//            if( progressDialog!= null && progressDialog.isShowing()){
//                progressDialog.dismiss();
//            }
            //Call inline function
            responseFromRest();
        }
    }
}
