package com.igsdigital.premierfeedback.customerfeedback;

/**
 * Created by Ali Jibran on 19/07/2016.
 */
public final class Constants {

    /**    Application Specific            */
    public static final String TAG = "KC";

//    public static final String APP_URL="http://192.168.8.106/baf-pf/public/api/";
//    public static final String APP_URL="http://192.168.0.116/baf-pf/public/api/";
    public static final String APP_URL = "http://igs-digital.net/baf-pf/public/api/";
    public static final String APP_LOGIN = APP_URL + "login?";
    public static final String APP_LOGOUT = APP_URL + "logout?";
    public static final String APP_POST_FEEDBACK = APP_URL + "postfeedback?";
    public static final String APP_READ_FEEDBACK = APP_URL + "readfeedback?";
    public static final String APP_EMAIL_FEEDBACK = APP_URL + "emailfeedback?";

    public static final String PREFS_NAME = "alfalahfeedback";

    public static final String HAPPY = "happy";
    public static final String INDIFFERENT = "indifferent";
    public static final String ANGRY = "angry";

    public static final int CUSTOMER_HAPPY = 1;
    public static final int CUSTOMER_INDIFFERENT = 2;
    public static final int CUSTOMER_ANGRY = 3;

    public static final String INTENT_SENT_BY = "sent_by";
}
