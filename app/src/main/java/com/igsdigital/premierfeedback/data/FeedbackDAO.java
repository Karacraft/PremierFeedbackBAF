package com.igsdigital.premierfeedback.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.igsdigital.premierfeedback.BuildConfig;
import com.igsdigital.premierfeedback.customerfeedback.Constants;

import java.sql.SQLException;

/**
 * Created by       duke / Kara Craft
 * For Project      Customer Feed Back
 * Dated            Jan 30 2016
 * File Name
 * Comments
 */
public class FeedbackDAO
{
    //Database Fields
    private SQLiteDatabase  sqLiteDatabase;
    private SqlHelper   sqlHelper;
    private Context     context;

    private String[]    allColumns = {
            sqlHelper.COLUMN_ID
            ,sqlHelper.COLUMN_CREATED_AT
            ,sqlHelper.COLUMN_HAPPY
            ,sqlHelper.COLUMN_INDIFFERENT
            ,sqlHelper.COLUMN_ANGRY
            ,sqlHelper.COLUMN_STAFFATTITUDE
            ,sqlHelper.COLUMN_LACKOFAWARENESS
            ,sqlHelper.COLUMN_STAFFUNAVAILABILITY
            ,sqlHelper.COLUMN_SYSTEMDOWNTIME
            ,sqlHelper.COLUMN_TRANSACTIONTIME
//            ,sqlHelper.COLUMN_COMMENTS
            ,sqlHelper.COLUMN_CUSTOMERNAME
            ,sqlHelper.COLUMN_CUSTOMERACCOUNT
            ,sqlHelper.COLUMN_USERNAME
    };

    //Constructor
    public FeedbackDAO(Context context)
    {
        this.context = context;
        sqlHelper = new SqlHelper(context);
        //open the database
        try
        {
            open();
        } catch (SQLException e)
        {
            //This won't compile with release built
            if (BuildConfig.DEBUG)
            {
                Log.d(Constants.TAG, "FeedbackDAO->: SQLEXCEPTION on Opening Database " + e.getMessage());
            }
            //e.printStackTrace();
        }
    }
    //-------------open()----------------------------
    public void open()throws SQLException {
        sqLiteDatabase = sqlHelper.getWritableDatabase();
    }
    //------------close()-----------------------------
    public void close(){
        sqLiteDatabase.close();
    }
    //-------------createFeedback()-------------------
    public void addFeedback(Feedback feedback){
        ContentValues contentValues = new ContentValues();
        contentValues.put(sqlHelper.COLUMN_ID,feedback.get_id());
        contentValues.put(sqlHelper.COLUMN_CREATED_AT,feedback.getCreated_at());
        contentValues.put(sqlHelper.COLUMN_HAPPY,feedback.getHappy());
        contentValues.put(sqlHelper.COLUMN_INDIFFERENT,feedback.getIndifferent());
        contentValues.put(sqlHelper.COLUMN_ANGRY,feedback.getAngry());
        contentValues.put(sqlHelper.COLUMN_STAFFATTITUDE,feedback.getStaffattitude());
        contentValues.put(sqlHelper.COLUMN_LACKOFAWARENESS,feedback.getLackofawareness());
        contentValues.put(sqlHelper.COLUMN_STAFFUNAVAILABILITY,feedback.getStaffunavailability());
        contentValues.put(sqlHelper.COLUMN_SYSTEMDOWNTIME,feedback.getSystemdowntime());
        contentValues.put(sqlHelper.COLUMN_TRANSACTIONTIME,feedback.getTransactiontime());
//        contentValues.put(sqlHelper.COLUMN_COMMENTS,feedback.getComments());
        contentValues.put(sqlHelper.COLUMN_CUSTOMERNAME,feedback.getCustomerName());
        contentValues.put(sqlHelper.COLUMN_CUSTOMERACCOUNT,feedback.getCustomerAccountNumber());
        contentValues.put(sqlHelper.COLUMN_USERNAME,feedback.getUsername());

        //Insert the row in table
        sqLiteDatabase.insert(SqlHelper.TABLE_FEEDBACK,null,contentValues);
        //This won't compile with release built
        if (BuildConfig.DEBUG)
        {
            Log.d(Constants.TAG, "FeedbackDAO->addFeedback: INSERTED " + feedback.toString());
        }
    }
    //-------------printData()-----------------------//
    public Cursor getFeedbacks(){
        Cursor cursor = null;
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + sqlHelper.TABLE_FEEDBACK,null);
        if (cursor !=null){
            cursor.moveToFirst();
        }
        return cursor;
    }
    //-------------deleteFeedback()-------------------
    /** Delete all rows from the table */
    public void deleteFeedback(){
        sqLiteDatabase.execSQL("DELETE FROM " + SqlHelper.TABLE_FEEDBACK);
    }
    //-------------getFeedBackByDate()----------------
    public void getFeedbackByDate(){

    }
}
