package com.igsdigital.premierfeedback.account;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by       duke / Kara Craft
 * For Project      Customer Feed Back
 * Dated            Jan 26 2016
 * File Name        StubProvider.java
 * Comments         Link:http://developer.android.com/training/sync-adapters/creating-stub-provider.html
 *                  Define an implementation of ContentProvider that stubs out
 *                  all methods (Use Acutal COntent Provider with actual app)
 */
public class StubProvider extends ContentProvider
{

    public static final String KEY_AUTHORITY = "com.igsdigital.account";

    /**
     * Always return true, indicating that the
     * provider loaded correctly.
     */

    @Override
    public boolean onCreate()
    {
        return true;
    }

    /**
     * query() always returns no results
     *
     */
    @Override
    public Cursor query(
            Uri uri
            , String[] projection
            , String selection
            , String[] selectionArgs
            , String sortOrder
            )
    {
        return null;
    }

    /**
     * Return no type for MIME type
     */
    @Override
    public String getType(Uri uri)
    {
        return null;
    }

    /**
     * insert() always returns null (no URI)
     */
    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        return null;
    }

    /**
     * delete() always returns "no rows affected" (0)
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        return 0;
    }

     /**
     * update() always returns "no rows affected" (0)
     */
    @Override
    public int update(
            Uri uri
            , ContentValues values
            , String selection
            , String[] selectionArgs
            )
    {
        return 0;
    }
}
