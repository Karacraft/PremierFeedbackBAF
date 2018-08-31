package com.igsdigital.premierfeedback.account;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by       duke / Kara Craft
 * For Project      Customer Feed Back
 * Dated            Jan 29 2016
 * File Name        SyncService.java
 * Comments         SyncService.java for SyncAdapter
 *                  together they both download upload data from server
 *                  using content provider is advised.
 *
 *                  This Service will bind with syncadapter, allowing us to call onPerformSync()
 *                  to upload/download data from/to Server.
 */
public class SyncService extends Service
{

    // Storage for an instance of the sync adapter
    private static SyncAdapter sSyncAdapter = null;
    // Object to use as a thread-safe lock
    private static final Object sSyncAdapterLock = new Object();

    /**
    * Instantiate the sync adapter object.
    */
    @Override
    public void onCreate() {
        /**
         * Create the sync adapter as a singleton.
         * Set the sync adapter as syncable
         * Disallow parallel syncs
         */
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null) {
                sSyncAdapter = new SyncAdapter(getApplicationContext(), true);
            }
        }
    }
    /**
     * Return an object that allows the system to invoke
     * the sync adapter.
     *
     */
    @Override
    public IBinder onBind(Intent intent) {
        /**
         * Get the object that allows external processes
         * to call onPerformSync(). The object is created
         * in the base class code when the SyncAdapter
         * constructors call super()
         */
        return sSyncAdapter.getSyncAdapterBinder();
    }

}
