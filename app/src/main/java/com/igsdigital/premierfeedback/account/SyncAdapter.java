package com.igsdigital.premierfeedback.account;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.igsdigital.premierfeedback.BuildConfig;

/**
 * Created by       duke / Kara Craft
 * For Project      Customer Feed Back
 * Dated            Jan 26 2016
 * File Name        SyncAdapter.java
 * Comments         Link :http://developer.android.com/training/sync-adapters/creating-sync-adapter.html
 *                  Handle the transfer of data between a server and an
 *                  app, using the Android sync adapter framework.
 *                  The Class needs a SyncService to work properly
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter
{

    public static final String KEY_DATASYNC_AUTHORITY  = "com.igsdigital.datasync.provider";

    // Global variables
    // Define a variable to contain a content resolver instance
    ContentResolver mContentResolver;

    /**
     * Setup the SyncAdapter
     */
    public SyncAdapter(Context context, boolean autoInitialize)
    {
        super(context, autoInitialize);
        /**
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();
    }

    /**
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     */
    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs)
    {
        super(context, autoInitialize, allowParallelSyncs);
        /**
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();
    }

    /** The sync adapter component does not automatically do data transfer.
     *  Instead, it encapsulates your data transfer code, so that the sync adapter framework
     *  can run the data transfer in the background, without involvement from your app.
     *  When the framework is ready to sync your application's data, it invokes your implementation of the method
     *
     * The entire
     * sync adapter runs in a background thread, so you don't have to set
     * up your own background processing.
     */
    @Override
    public void onPerformSync(
            Account account /** An Account object associated with the event that triggered the sync adapter. If your server doesn't use accounts, you don't need to use the information in this object. */
            , Bundle extras /** A Bundle containing flags sent by the event that triggered the sync adapter.  */
            , String authority /** The authority of a content provider in the system. Your app has to have access to this provider. Usually, the authority corresponds to a content provider in your own app. */
            , ContentProviderClient provider /** If you're using a content provider to store data for your app, you can connect to the provider with this object. Otherwise, you can ignore it. */
            , SyncResult syncResult /**     A SyncResult object that you use to send information to the sync adapter framework. */
            )
    {

        //This won't compile with release built
        if (BuildConfig.DEBUG)
        {
            Log.d("alfalah", "SyncAdapter->onPerformSync: ");
        }


        /** Following Task could be performed here
         * Connecting to a server
         * Downloading & Uploading data (user content provider or database, also handle errors
         * Handling data conflicts (if data is current or not, use own code)
         * Clean Up (close server connections, remove temp files etc)
         */
    }
}
