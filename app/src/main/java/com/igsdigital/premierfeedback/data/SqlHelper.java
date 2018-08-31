package com.igsdigital.premierfeedback.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.igsdigital.premierfeedback.BuildConfig;

/**
 * Created by       duke / Kara Craft
 * For Project      Customer Feed Back
 * Dated            Jan 30 2016
 * File Name
 * Comments
 */
public class SqlHelper extends SQLiteOpenHelper
{

    private static final String TAG = "karacraft";

    public static final String DATABASE_NAME = "alfalah.feedback.db";
    private static final int DATABASE_VERSION = 1;

    /**   Common Columns */
    public static final String COLUMN_ID                    = "_id";
    public static final String COLUMN_CREATED_AT            = "created_at";


    /**   Feedback Table  */
    public static final String TABLE_FEEDBACK               = "feedback";
    public static final String COLUMN_HAPPY                 = "happy";
    public static final String COLUMN_INDIFFERENT           = "indifferent";
    public static final String COLUMN_ANGRY                 = "angry";
    public static final String COLUMN_STAFFATTITUDE         = "staffattitude";
    public static final String COLUMN_LACKOFAWARENESS       = "lackofawareness";
    public static final String COLUMN_STAFFUNAVAILABILITY   = "staffunavailability";
    public static final String COLUMN_SYSTEMDOWNTIME        = "systemdowntime";
    public static final String COLUMN_TRANSACTIONTIME       = "transactiontime";
//    public static final String COLUMN_COMMENTS              = "comments";
    public static final String COLUMN_CUSTOMERNAME          = "customername";        //The Bank User
    public static final String COLUMN_CUSTOMERACCOUNT       = "customeraccountnumber";
    public static final String COLUMN_USERNAME              = "username";    //The Priority Customer

    /**   Customers Table  */
    public static final String TABLE_CUSTOMERS = "customers";
    public static final String CUSTOMER_NAME ="customer_name";
    public static final String CUSTOMER_EMAIL="customer_email";
    public static final String CUSTOMER_ACCOUNTNUMBER ="customer_accountnumber";
    public static final String CUSTOMER_CONTACTNUMBER1 ="customer_contactnumber1";

    /**     Create Table SQL    */
    /** Feedback Table Creation Statement */
    private static final String FEEDBACK_TABLE_CREATE = "CREATE TABLE "
            + TABLE_FEEDBACK
            + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CREATED_AT + " TEXT, "
            + COLUMN_HAPPY + " INTEGER,"
            + COLUMN_INDIFFERENT + " INTEGER,"
            + COLUMN_ANGRY + " INTEGER,"
            + COLUMN_STAFFATTITUDE + " INTEGER,"
            + COLUMN_LACKOFAWARENESS + " INTEGER,"
            + COLUMN_STAFFUNAVAILABILITY + " INTEGER,"
            + COLUMN_SYSTEMDOWNTIME + " INTEGER,"
            + COLUMN_TRANSACTIONTIME + " INTEGER,"
//            + COLUMN_COMMENTS + " TEXT,"
            + COLUMN_CUSTOMERNAME + " TEXT,"
            + COLUMN_CUSTOMERACCOUNT + " TEXT,"
            + COLUMN_USERNAME + " TEXT "
            + ");";

    /** Customer Table Creation Statement */
//    private static final String CUSTOMER_TABLE_CREATE = "CREATE TABLE "
//            + TABLE_CUSTOMERS
//            + "("
//            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//            + CUSTOMER_NAME + " TEXT,"
//            + CUSTOMER_EMAIL + " TEXT, "
//            + CUSTOMER_ACCOUNTNUMBER + " TEXT,"
//            + CUSTOMER_CONTACTNUMBER1 + " TEXT"
//            + ");";


    //This creates the Database in Android System
    public SqlHelper(Context context)
    {
        // replace name and version with your own DATABASE_NAME & DATABASE_VERSION
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

//    //---------Open Connection
//    public void Open() throws SQLException
//    {
//        this.getWritableDatabase();
//    }
//
//    //-----------Close connecton
//    public void Close(){
//        this.close();
//    }


    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase db)
    {
//        db.execSQL(CUSTOMER_TABLE_CREATE);
        db.execSQL(FEEDBACK_TABLE_CREATE);
    }

    // Method is called during an upgrade of the database,
    // e.g. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //This won't compile with release built
        if (BuildConfig.DEBUG)
        {
            Log.d(TAG, "SqlHelper->onUpgrade: Upgrading the database from Version " + oldVersion + " to " + newVersion);
        }
        //Clear All data
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEEDBACK);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS);

        //recreate the db
        onCreate(db);
    }
}
