package com.igsdigital.premierfeedback.account;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.igsdigital.premierfeedback.customerfeedback.LoginActivity;

/**
 * Created by       duke / Kara Craft
 * For Project      Customer Feed Back
 * Dated            Jan 26 2016
 * File Name        Authenticator.java
 * Comments         Link : http://developer.android.com/training/sync-adapters/creating-authenticator.html
 *                  This is a stub Authenticator. To Use an actual account, Please implement Data
 *                  In current state, with AuthenticateService.java, The "Customer Feedback" is
 *                  visible in android accounts data, but it doesn't show any thing.
 */
public class Authenticator extends AbstractAccountAuthenticator
{

    //ACCOUNT TYPE
    public static final String KEY_ACCOUNT_TYPE  ="com.igsdigital.premierfeedback";
    public static final String KEY_PROVIDER_NAME ="com.igsdigital.StubProvider";

    Context context;
    //Simple Constructor
    public Authenticator(Context context)
    {
        super(context);
        this.context = context;
    }

    //Editing Properites is not supported
    @Override
    public Bundle editProperties(
            AccountAuthenticatorResponse response
            , String accountType)
    {
        return null;
    }

    //Add new Accounts Here
    @Override
    public Bundle addAccount(
            AccountAuthenticatorResponse response
            , String accountType        //in our case, its com.igs.alfalah
            , String authTokenType
            , String[] requiredFeatures
            , Bundle options
            ) throws NetworkErrorException
    {
        //TODO::here you can stop user from adding more accounts
        //We want to add account via LoginActivity
        final Intent intent = new Intent(context, LoginActivity.class);
        //Add Extras in here
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT,intent);
        //return null;
        return bundle;
    }
    //ignore attempts to confirm Credentials
    @Override
    public Bundle confirmCredentials(
            AccountAuthenticatorResponse response
            , Account account
            , Bundle options
            ) throws NetworkErrorException
    {
        return null;
    }
    //Getting an authentication Token is not supported
    @Override
    public Bundle getAuthToken(
            AccountAuthenticatorResponse response
            , Account account
            , String authTokenType
            , Bundle options
            ) throws NetworkErrorException
    {
        return null;
    }
    //Getting an authentication Token Label is not supported
    @Override
    public String getAuthTokenLabel(String authTokenType)
    {
        return null;
    }
    //Updating credentials is not supported
    @Override
    public Bundle updateCredentials(
            AccountAuthenticatorResponse response
            , Account account
            , String authTokenType
            , Bundle options
            ) throws NetworkErrorException
    {
        return null;
    }
    //Checking features isnot supported
    @Override
    public Bundle hasFeatures(
            AccountAuthenticatorResponse response
            , Account account
            , String[] features
            ) throws NetworkErrorException
    {
        return null;
    }
}
