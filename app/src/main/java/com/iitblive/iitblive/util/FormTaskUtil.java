package com.iitblive.iitblive.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.iitblive.iitblive.MainActivity;

/**
 * Created by bijoy on 7/4/15.
 */
public class FormTaskUtil {
    public static void loginUtil(Context context, String ldap, String name) {
        Functions.saveSharedPreference(context, Constants.SP_USERNAME, ldap);
        Functions.saveSharedPreference(context, Constants.SP_NAME, name);
        LoginFunctions.userLoggedIn(context);

        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        ((Activity) context).finish();
    }
}
