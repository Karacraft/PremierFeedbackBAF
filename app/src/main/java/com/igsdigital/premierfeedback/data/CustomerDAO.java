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
 * Dated            Jan 31 2016
 * File Name
 * Comments
 */
public class CustomerDAO
{

    private static final String TAG = "karacraft";

    //Database Fields
    //Database Fields
    private SQLiteDatabase sqLiteDatabase;
    private SqlHelper   sqlHelper;
    private Context context;

    private String[] allColumns = {
            SqlHelper.COLUMN_ID
            ,SqlHelper.CUSTOMER_NAME
            ,SqlHelper.CUSTOMER_EMAIL
            ,SqlHelper.CUSTOMER_ACCOUNTNUMBER
            ,SqlHelper.CUSTOMER_CONTACTNUMBER1
    };
    //----------------------------------Constructor-----------------------------//
    public CustomerDAO(Context context)
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
                Log.d(TAG, "CustomerDAO->CustomerDAO: SQLEXCEPTION on Opening Database " + e.getMessage());
            }
            //e.printStackTrace();
        }
    }

    public void open() throws SQLException{
        sqLiteDatabase = sqlHelper.getWritableDatabase();
    }

    public void close(){
        sqLiteDatabase.close();
    }
    //-------------createFeedback()-------------------
    public void addCustomer(Customer customer){
        ContentValues contentValues = new ContentValues();
        contentValues.put(sqlHelper.COLUMN_ID,customer.get_id());
        contentValues.put(sqlHelper.CUSTOMER_CONTACTNUMBER1,customer.getCustomer_contactnumber1());
        contentValues.put(sqlHelper.CUSTOMER_NAME,customer.getCustomer_name());
        contentValues.put(sqlHelper.CUSTOMER_EMAIL,customer.getCustomer_email());
        contentValues.put(sqlHelper.CUSTOMER_ACCOUNTNUMBER,customer.getCustomer_accountnumber());

        //Insert the row in table
        sqLiteDatabase.insert(SqlHelper.TABLE_CUSTOMERS,null,contentValues);
        //This won't compile with release built
        if (BuildConfig.DEBUG)
        {
            Log.d(Constants.TAG, "FeedbackDAO->addFeedback: INSERTED " + customer.toString());
        }
    }
    //-------------fetchAllCustomers()----------------
    public Cursor fetchAllCustomers(){
        String query ="SELECT * FROM " + sqlHelper.TABLE_CUSTOMERS + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetchCustomersByName(String name) throws SQLException{

        Cursor cursor=null;

        if(name == null || name.length() == 0){
            //Log.d(Constants.TAG, "fetchCustomersByName: Its o");
            cursor = sqLiteDatabase.query(SqlHelper.TABLE_CUSTOMERS,
                    new String[] {
                            SqlHelper.COLUMN_ID
                            ,SqlHelper.CUSTOMER_NAME
                            ,SqlHelper.CUSTOMER_EMAIL
                            ,SqlHelper.CUSTOMER_ACCOUNTNUMBER
                            ,SqlHelper.CUSTOMER_CONTACTNUMBER1},
                    null,null,null,null,null);
        }else {
            cursor = sqLiteDatabase.query(true,SqlHelper.TABLE_CUSTOMERS,
                    new String[] {
                            SqlHelper.COLUMN_ID
                            ,SqlHelper.CUSTOMER_NAME
                            ,SqlHelper.CUSTOMER_EMAIL
                            ,SqlHelper.CUSTOMER_ACCOUNTNUMBER
                            ,SqlHelper.CUSTOMER_CONTACTNUMBER1},
                    SqlHelper.CUSTOMER_NAME + " LIKE '%" + name +  "%'" ,null,null,null,null,null);
        }
        if(cursor != null){
            cursor.moveToFirst();
        }

        return cursor;
    }
    //-------------deleteAllCustomer()-------------------
    /** Delete all rows from the table */
    public void deleteCustomers(){
        sqLiteDatabase.execSQL("DELETE FROM " + SqlHelper.TABLE_CUSTOMERS);
    }

}
