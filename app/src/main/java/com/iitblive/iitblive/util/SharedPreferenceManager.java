package com.iitblive.iitblive.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bijoy on 7/23/15.
 */
public class SharedPreferenceManager {
    public static final String SHARED_PREFERENCES = "IITBAPP";

    //Load the data from the shared preferences
    public static String load(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCES, Activity.MODE_PRIVATE);
        String strValue = sp.getString(key, "");
        return strValue;
    }

    //Saves the data into the shared preferences
    public static void save(Context context,
                            String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCES, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static final class Tags {
        public static final String NAME = "NAME";
        public static final String USERNAME = "USERNAME";
        public static final String LOGGED_IN = "LOGGED_IN";
        public static final String FIRST_TIME = "FIRST_TIME";
        public static final String USER_ID = "USER_ID";
        public static final String REGISTRATION_ID = "GCM_REG_ID";
        public static final String GCM_REGISTERED = "GCM_REG_STATUS";
        public static final String TRUE = "TRUE";
        public static final String FALSE = "FALSE";
        public static final String OFFLINE = "OFFLINE";
        public static final String EMPTY = "";

    }

}
