package com.igsdigital.premierfeedback.customerfeedback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by Ali Jibran on 21/07/2016.
 */
public class Helper {

    //------------------readData()-----------------------------------
    public static String readData(HttpURLConnection conn){

        String mResponse="";
        String line="";
        BufferedReader mBufferedReader = null;
        //Try to Read the InputStream
        try
        {
            mBufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = mBufferedReader.readLine()) != null) {
                mResponse = mResponse + line;
            }
            mBufferedReader.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        return mResponse;
    }

    public static String replaceSpaces(String text){
        String result = text.replace(" ","%20");

        return result;
    }
}
