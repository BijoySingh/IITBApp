package com.iitblive.iitblive.util;

import android.content.Context;

/**
 * Created by Bijoy on 6/27/2015.
 */
public class LoginFunctions {
  public static void userLoggedIn(Context context) {
    Functions.saveSharedPreference(context, Constants.SP_LOGGED_IN, Constants.SP_TRUE);
  }

  public static void userOffline(Context context) {
    Functions.saveSharedPreference(context, Constants.SP_LOGGED_IN, Constants.SP_OFFLINE);
  }

  public static boolean isUserLoggedIn(Context context) {
    return Functions.loadSharedPreference(
        context,
        Constants.SP_LOGGED_IN).contentEquals(Constants.SP_TRUE);
  }

  public static boolean isUserOffline(Context context) {
    return Functions.loadSharedPreference(
        context,
        Constants.SP_LOGGED_IN).contentEquals(Constants.SP_OFFLINE);
  }

  public static void logoutUser(Context context) {
    Functions.saveSharedPreference(context, Constants.SP_USERNAME, Constants.SP_EMPTY);
    Functions.saveSharedPreference(context, Constants.SP_NAME, Constants.SP_EMPTY);
    Functions.saveSharedPreference(context, Constants.SP_LOGGED_IN, Constants.SP_OFFLINE);
  }
}
